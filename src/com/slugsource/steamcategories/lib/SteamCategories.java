/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib;

import com.slugsource.steamcategories.lib.vdf.Node;
import java.io.File;

/**
 *
 * @author Nathan Fearnley
 */
public class SteamCategories
{
    public static void main(String[] args)
    {
        // Load the file
        Node rootNode;
        File file = new File("F:\\Programming\\SteamCategories\\sharedconfig.vdf");

        rootNode = Node.readFromFile(file);
        
        String[] path = {"Software", "Valve", "Steam", "apps", "72850", "tags"};
        rootNode.setValue(path, "0", "FPS RPG");
            
        Node.writeToFile(new File("F:\\Programming\\SteamCategories\\testoutput.vdf"), rootNode);
    }
}
