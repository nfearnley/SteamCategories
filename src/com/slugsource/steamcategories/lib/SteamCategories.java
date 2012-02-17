/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib;

import com.slugsource.steamcategories.gui.NotValidFileException;
import com.slugsource.vdf.lib.Node;
import java.io.File;

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
    

    public SteamCategories(File file) throws NotValidFileException
    {
        rootNode = rootNode.readFromFile(file);
        if (rootNode.getName() != rootName)
        {
            throw new NotValidFileException("This is not a valid shared config file.");
        }
    
        appsNode = rootNode.getNode(appsPath, appsName);
    }
            
}
