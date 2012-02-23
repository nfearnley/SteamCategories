/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib;

import com.slugsource.vdf.lib.InvalidFileException;
import com.slugsource.vdf.lib.Node;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Nathan Fearnley
 */
public class SteamCategoryFile
{

    private Node rootNode;
    private Node appsNode;
    private String steamID;
    private File file;
    private final String[] appsPath =
    {
        "Software", "Valve", "Steam"
    };
    private final String appsName = "apps";
    private final String rootName = "UserLocalConfigStore";

    public SteamCategoryFile()
    {
    }

    public SteamCategoryFile(File file)
    {
        this.file = file;
    }

    public void readFromFile(File file) throws InvalidFileException, IOException
    {
        this.file = file;
        readFromFile();
    }

    public void readFromFile() throws InvalidFileException, IOException
    {
        if (file == null)
        {
            throw new NullPointerException("File cannot be null.");

        }
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

    /**
     * Gets the category of the given app
     *
     * @param appId The AppID of the steam app
     * @return Category if set, null if not set
     */
    public String getCategory(String appId)
    {
        if (appId == null)
        {
            throw new NullPointerException("App cannot be null.");
        }
        if (appsNode == null)
        {
            return null;
        }
        
        String[] path =
        {
            appId, "tags"
        };
        String name = "0";
        if (appId.equals("72850"))
        {
            System.out.println("Skyrim");
        }
        String value = appsNode.getValue(path, name);
        return value;
    }

    /**
     * Sets the category of the given app
     *
     * @param appId The AppID of the steam app
     * @param category The category to add the app to. If null, category is
     * removed.
     * @return True on success, False on failure
     */
    public boolean setCategory(String appId, String category)
    {
        if (appId == null)
        {
            throw new NullPointerException("App cannot be null.");
        }
        if (category == null)
        {
            return removeCategory(appId);
        }

        String[] path =
        {
            appId, "tags"
        };
        appsNode.setValue(path, "0", category);

        return true;
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
        this.file = file;

    }

    /**
     * Save configuration to file
     */
    public void writeToFile() throws IOException
    {
        rootNode.writeToFile(file);
    }

    /**
     * Removes the category of the given app
     *
     * @param appId The AppID of the steam app
     * @return True on success, False on failure
     */
    private boolean removeCategory(String appId)
    {
        if (appId == null)
        {
            throw new NullPointerException("App cannot be null.");
        }

        Node appNode = appsNode.getNode(appId);
        boolean result = true;
        if (appNode != null)
        {
            result = appNode.delNode(new Node("tags"));
        }
        return result;
    }
}
