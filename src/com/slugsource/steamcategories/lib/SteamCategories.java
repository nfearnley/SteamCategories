/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib;

import com.slugsource.vdf.lib.InvalidFileException;
import com.slugsource.vdf.lib.Node;
import com.slugsource.vdf.lib.NodeNotFoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Savepoint;
import java.util.List;

/**
 *
 * @author c260683
 */
public class SteamCategories
{

    private Node rootNode;
    private Node appsNode;
    private List<SteamApp> apps;
    private List<SteamApp> oldApps;
    private String steamID;
    private File file;
    private final String[] appsPath =
    {
        "Software", "Valve", "Steam", "apps"
    };
    private final String appsName = "apps";
    private final String rootName = "UserLocalConfigStore";

    /**
     * Constructor that takes file to read from.
     *
     * @param file File to load from
     * @param steamId Steam ID of user
     * @throws FileNotFoundException If the file could not found
     * @throws IOException If there is a problem reading from the filesystem
     * @throws InvalidFileException If the format of the file is incorrect
     */
    public SteamCategories(File file, String steamId) throws FileNotFoundException, IOException, InvalidFileException
    {
        if (file == null)
        {
            throw new NullPointerException("File cannot be null.");
        }
        if (steamId == null)
        {
            throw new NullPointerException("SteamID cannot be null.");
        }

        this.file = file;
        this.steamID = steamId;

        // Load configuration from file
        readFromFile(file);

        // Load games from xml
        readGamesFromSteamId(steamId);

        // Sync xml and configuration
        syncNodeToApps();
    }

    // TODO: Add javadocs
    private void readFromFile(File file) throws FileNotFoundException, IOException, InvalidFileException
    {
        if (file == null)
        {
            throw new NullPointerException("File cannot be null.");

        }
        this.file = file;
        this.rootNode = Node.readFromFile(file);
        if (!rootNode.getName().equals(rootName))
        {
            throw new InvalidFileException("This is not a valid shared config file.");
        }

        this.appsNode = rootNode.getNode(appsPath, appsName);
        if (this.appsNode == null)
        {
            throw new InvalidFileException("This is a not a valid shared config file.");
        }
    }

    private void syncOldApps()
    {
        oldApps = cloneList(apps);
    }
    
    // TODO: Add javadocs
    private void readGamesFromSteamId(String steamId) throws IOException
    {
        if (steamId == null)
        {
            throw new NullPointerException("SteamID cannot be null.");
        }

        apps = SteamApp.readGamesFromSteamId(steamId);
        oldApps = apps.subList(0, apps.size() - 1);
    }

    /**
     * Returns whether or not this file has changed since last save
     *
     * @return True if file has changed since last save, false if it has not
     * changed.
     */
    public boolean isDirty()
    {
        boolean result = false;
        for (SteamApp app : apps)
        {
            if (isDirty(app))
            {
                result = true;
                break;
            }
        }
        return result;
    }

    private boolean isDirty(SteamApp app)
    {
        String category = app.getCategory();

        int oldIndex = oldApps.indexOf(app);
        SteamApp oldApp = oldApps.get(oldIndex);

        String oldCategory = oldApp.getCategory();

        boolean result = false;
        if (category == null && oldCategory == null)
        {
            result = false;
        }
        else
        {
            result = !category.equals(oldCategory);
        }

        return result;
    }

    public List<SteamApp> getGamesList()
    {
        return cloneList(apps);
    }
    
    public String getGameCategory(String appId)
    {
        if (appId == null)
        {
            throw new NullPointerException("AppId cannot be null.");
        }
        int index = apps.indexOf(new SteamApp("", appId));
        if (index == -1)
        {
            return null;
        }

        String category = apps.get(index).getCategory();
        return category;
    }

    public boolean setGameCategory(String appId, String category)
    {
        if (appId == null)
        {
            throw new NullPointerException("AppId cannot be null.");
        }
        int index = apps.indexOf(new SteamApp("", appId));
        if (index == -1)
        {
            return false;
        }

        apps.get(index).setCategory(category);
        return true;
    }

    /**
     * Sets the category of the given game
     *
     * @param app The AppID of the steam game
     * @return Category if set, null if not set
     */
    private String getNodeGameCategory(String app)
    {
        if (app == null)
        {
            throw new NullPointerException("App cannot be null.");
        }

        String[] path =
        {
            app, "tags"
        };
        String value = appsNode.getValue(path, "0");
        return value;
    }

    /**
     * Sets the category of the given game
     *
     * @param appId The AppID of the steam game
     * @param category The category to add the game to. If null, category is
     * removed.
     * @return True on success, False on failure
     */
    private boolean setNodeGameCategory(String appId, String category)
    {
        if (appId == null)
        {
            throw new NullPointerException("App cannot be null.");
        }
        if (category == null)
        {
            return removeNodeGameCategory(appId);
        }
        int index = apps.indexOf(new SteamApp("", appId));
        if (index == -1)
        {
            return false;
        }

        String[] path =
        {
            appId, "tags"
        };
        appsNode.setValue(path, "0", category);
        apps.get(index).setCategory(category);

        return true;
    }

    /**
     * Removes the category of the given game
     *
     * @param appId The AppID of the steam game
     * @return True on success, False on failure
     */
    private boolean removeNodeGameCategory(String appId)
    {
        if (appId == null)
        {
            throw new NullPointerException("App cannot be null.");
        }
        int index = apps.indexOf(new SteamApp("", appId));
        if (index == -1)
        {
            return false;
        }

        Node gameNode = appsNode.getNode(appId);
        boolean result = true;
        if (gameNode != null)
        {
            result = gameNode.delNode(new Node("tags"));
        }
        return result;
    }

    private void syncAppsToNode()
    {
        for (SteamApp app : apps)
        {
            if (isDirty(app))
            {
                setNodeGameCategory(app.getAppid(), app.getCategory());
            }
        }
    }
    
    private void syncNodeToApps()
    {
        for (SteamApp app : apps)
        {
            String appId = app.getAppid();
            String category = getNodeGameCategory(appId);
            app.setCategory(category);
        }
    }
    
    /**
     * Save configuration to file specified
     *
     * @param file File to save to
     */
    public void writeToFile(File file) throws IOException
    {
        syncAppsToNode();
        if (file == null)
        {
            throw new NullPointerException("File cannot be null.");
        }
        rootNode.writeToFile(file);
        syncOldApps();
        
    }
    
    public List<SteamApp> cloneList(List<SteamApp> list)
    {
        return apps.subList(0, apps.size() - 1);
    }

    /**
     * Save configuration to file
     */
    public void writeToFile() throws IOException
    {
        writeToFile(file);
    }
}
