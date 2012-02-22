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

/**
 *
 * @author c260683
 */
public class SteamCategories
{

    private Node rootNode;
    private Node appsNode;
    private SteamApp[] apps;
    private String steamID;
    private final String[] appsPath =
    {
        "Software", "Valve", "Steam", "apps"
    };
    private final String appsName = "apps";
    private final String rootName = "UserLocalConfigStore";
    private File file;
    private boolean dirty = false;

    /**
     * Constructor that takes file to read from.
     *
     * @param file File to load from
     * @param steamID Steam ID of user
     * @throws FileNotFoundException If the file could not found
     * @throws IOException If there is a problem reading from the filesystem
     * @throws InvalidFileException If the format of the file is incorrect
     */
    public SteamCategories(File file, String steamID) throws FileNotFoundException, IOException, InvalidFileException
    {
        if (file == null)
        {
            throw new NullPointerException("File cannot be null.");
        }
        if (steamID == null)
        {
            throw new NullPointerException("SteamID cannot be null.");
        }
        readFromFile(file);
        this.steamID = steamID;
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

    // TODO: Add javadocs
    private void readGamesFromXML(String steamID)
    {
        if (steamID == null)
        {
            throw new NullPointerException("SteamID cannot be null.");
        }

        // TODO: Load from XML file http://steamcommunity.com/id/{steamID}/games?xml=1
    }

    /**
     * Returns whether or not this file has changed since last save
     *
     * @return True if file has changed since last save, false if it has not
     * changed.
     */
    public boolean isDirty()
    {
        return dirty;
    }

    /**
     * Sets the category of the given game
     *
     * @param app The AppID of the steam game
     * @param category The category to add the game to
     */
    public void setGameCategory(String app, String category)
    {
        if (app == null)
        {
            throw new NullPointerException("App cannot be null.");
        }
        if (category == null)
        {
            throw new NullPointerException("Category cannot be null.");
        }

        String[] path =
        {
            app, "tags"
        };
        appsNode.setValue(path, "0", category);
        dirty = true;
    }

    /**
     * Removes the category of the given game
     *
     * @param app The AppID of the steam game
     * @return True on success, False on failure
     */
    public boolean removeGameCategory(String app)
    {
        if (app == null)
        {
            throw new NullPointerException("App cannot be null.");
        }

        Node gameNode = appsNode.getNode(app);
        boolean result = true;
        if (gameNode != null)
        {
            result = gameNode.delNode(new Node("tags"));
            dirty = true;
        }
        return result;
    }

    /**
     * Save configuration to file specified
     *
     * @param file File to save to
     */
    public void writeToFile(File file) throws IOException
    {
        if (file == null)
        {
            throw new NullPointerException("File cannot be null.");
        }
        rootNode.writeToFile(file);
        dirty = false;
    }

    /**
     * Save configuration to file
     */
    public void writeToFile() throws IOException
    {
        writeToFile(file);
    }
}
