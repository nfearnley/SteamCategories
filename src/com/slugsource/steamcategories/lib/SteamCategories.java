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
    private final String[] appsPath = {"Software", "Valve", "Steam", "apps"};
    private final String appsName = "apps";
    private final String rootName = "UserLocalConfigStore";
    private File file;
    
    //TODO: Add docs
    /**
     * Constructor that takes file to read from.
     * @param file
     * @throws FileNotFoundException
     * @throws IOException
     * @throws InvalidFileException 
     */
    public SteamCategories(File file) throws FileNotFoundException, IOException, InvalidFileException
    {
        this.file = file;
        rootNode = Node.readFromFile(file);
        if (!rootNode.getName().equals(rootName))
        {
            throw new InvalidFileException("This is not a valid shared config file.");
        }
        
        try
        {
            appsNode = rootNode.getNode(appsPath, appsName);
        } catch (NodeNotFoundException ex)
        {
            throw new InvalidFileException("This is a not a valid shared config file.");
        }
        
        
    }
    
    //TODO: Add docs
    /**
     * Sets the category of the given game
     * @param app
     * @param category 
     */
    public void setGameCategory(String app, String category)
    {
        String[] path = {app, "tags"};
        appsNode.setValue(path, "0", category);
    }
    
    
    //TODO: Add docs
    /**
     * Save configuration to file specified
     * @param file 
     */
    public void writeToFile(File file) throws IOException
    {
        rootNode.writeToFile(file);
    }
    
    //TODO: Add Docs
    /**
     * Save configuration to file
     */
    public void writeToFile() throws IOException
    {
        writeToFile(file);
    }
}
