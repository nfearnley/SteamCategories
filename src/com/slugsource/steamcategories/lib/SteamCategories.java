/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib;

import com.slugsource.steamcategories.lib.vdf.BranchNode;
import com.slugsource.steamcategories.lib.vdf.Node;
import com.slugsource.steamcategories.lib.vdf.ValueNode;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.naming.NameNotFoundException;

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
        
        try
        {
            Node apps = rootNode.getNode("Software").getNode("Valve").getNode("Steam").getNode("apps");
            String skyrimId = "72850";
            Node skyrim = apps.getNode("72850");
            if (skyrim == null)
            {
                skyrim = new BranchNode("72850");
                skyrim.addNode(new ValueNode("tags", "FPS RPG"));
                apps.addNode(skyrim);
            }
            else
            {
                Node tags = skyrim.getNode("tags");
                if (tags == null)
                {
                    skyrim.addNode(new ValueNode("tags", "FPS RPG"));
                }
                else
                {

                }
            }
        } catch (NodeNameNotUniqueException e)
        {
            System.out.println(e.getMessage());
            return;
        }
            
        Node.writeToFile(new File("F:\\Programming\\SteamCategories\\testoutput.vdf"), rootNode);
    }
}
