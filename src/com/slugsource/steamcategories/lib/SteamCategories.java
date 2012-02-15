/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib;

import com.slugsource.steamcategories.lib.vdf.Node;
import java.io.File;
import java.io.FileNotFoundException;

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
        try
        {
            rootNode = Node.readFromFile(file);
        } catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
            return;
        }
        
        System.out.println(rootNode.getName());
        rootNode.getNode("UserLocalConfigStore");
    }
}
