/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.gui;

import com.slugsource.steamcategories.lib.SteamCategories;
import com.slugsource.vdf.lib.InvalidFileException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nathan Fearnley
 */
public class SteamCategoriesTest
{
    public static void main(String[] args)
    {
        try
        {
            File inFile = new File("F:\\Programming\\SteamCategories\\sharedconfig.vdf");
            File outFile = new File("F:\\Programming\\SteamCategories\\testoutput.vdf");
            
            SteamCategories cats = new SteamCategories(inFile);
            
            cats.setGameCategory("72850", "FPS RPG");
            
            cats.writeToFile(outFile);
        } catch (IOException | InvalidFileException ex)
        {
            Logger.getLogger(SteamCategoriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
