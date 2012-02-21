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
     * @throws FileNotFoundException If the file could not found
     * @throws IOException If there is a problem reading from the filesystem
     * @throws InvalidFileException If the format of the file is incorrect
     */
    public SteamCategories(File file, String steamID) throws FileNotFoundException, IOException, InvalidFileException
    {
        readFromFile(file);
        
        
    }

    // TODO: Add javadocs
    private void readFromFile(File file) throws FileNotFoundException, IOException, InvalidFileException
    {
        this.file = file;
        this.rootNode = Node.readFromFile(file);
        if (!rootNode.getName().equals(rootName))
        {
            throw new InvalidFileException("This is not a valid shared config file.");
        }

        try
        {
            this.appsNode = rootNode.getNode(appsPath, appsName);
        } catch (NodeNotFoundException ex)
        {
            throw new InvalidFileException("This is a not a valid shared config file.");
        }

    }
    
    private void readGamesFromXML(String steamID)
    {
        // Load from XML file http://steamcommunity.com/id/{steamID}/games?xml=1
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
     * @throws NodeNotFoundException If the app or category was not found
     */
    public void removeGameCategory(String app)
    {
        try
        {
            appsNode.getNode(app);
            appsNode.delNode(new Node("tags"));
            dirty = true;
        } catch (NodeNotFoundException e)
        {
        }
    }

    /**
     * Save configuration to file specified
     *
     * @param file File to save to
     */
    public void writeToFile(File file) throws IOException
    {
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
