/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.gui;

import com.slugsource.vdf.lib.InvalidFileException;
import com.slugsource.vdf.lib.Node;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nathan Fearnley
 */
public class SteamCategories
{
    public static void main(String[] args)
    {
        try
        {
            // Load the file
            Node rootNode;
            File file = new File("F:\\Programming\\SteamCategories\\sharedconfig.vdf");

            rootNode = Node.readFromFile(file);
            
            String[] path = {"Software", "Valve", "Steam", "apps", "72850", "tags"};
            rootNode.setValue(path, "0", "FPS RPG");
                
            Node.writeToFile(new File("F:\\Programming\\SteamCategories\\testoutput.vdf"), rootNode);
        } catch (InvalidFileException | IOException ex)
        {
            Logger.getLogger(SteamCategories.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
