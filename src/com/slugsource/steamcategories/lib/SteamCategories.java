/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib;

import com.slugsource.vdf.lib.InvalidFileException;
import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 *
 * @author c260683
 */
public class SteamCategories
{

    private SteamCategoryFile categoryFile = null;
    private SteamAppList apps = null;

    public SteamCategories()
    {
        this.categoryFile = null;
        this.apps = null;
    }

    /**
     * Constructor that takes file to read from.
     *
     * @param file File to load from
     * @param steamId Steam ID of user
     */
    public SteamCategories(File file, String steamId)
    {
        if (file == null)
        {
            throw new NullPointerException("File cannot be null.");
        }
        if (steamId == null)
        {
            throw new NullPointerException("SteamID cannot be null.");
        }

        this.categoryFile = new SteamCategoryFile(file);
        this.apps = new SteamAppList(steamId);
    }

    private void syncAppsToCategoryFile()
    {
        for (String appId : getAppIdList())
        {
            if (apps.isDirty(appId))
            {
                String category = apps.getCategory(appId);
                categoryFile.setCategory(appId, category);
            }
        }
    }

    private void syncCategoryFileToApps()
    {
        for (String appId : getAppIdList())
        {
            String category = categoryFile.getCategory(appId);
            apps.setCategory(appId, category);
        }
    }
    
    public boolean setCategory(String appId, String category)
    {
        return apps.setCategory(appId, category);
    }
    
    public String getCategory(String appId)
    {
        return apps.getCategory(appId);
    }
    
    public String getName(String appId)
    {
        return apps.getName(appId);
    }


    public void readApps(String steamId) throws IOException
    {
        if (steamId == null)
        {
            throw new NullPointerException("SteamID cannot be null.");
        }

        SteamAppList apps = new SteamAppList(steamId);
        readApps();

    }

    public void readApps() throws IOException
    {
        apps.readAppsFromSteamId();
        syncCategoryFileToApps();
    }
    
    public Set<String> getAppIdList()
    {
        return apps.getAppIdList();
    }
    
    public Set<String> getCategoryList()
    {
        return apps.getCategoryList();
    }

    public void readCategories(File file) throws InvalidFileException, IOException
    {
        categoryFile = new SteamCategoryFile(file);
        readCategories();
    }

    public void readCategories() throws InvalidFileException, IOException
    {
        categoryFile.readFromFile();
        syncCategoryFileToApps();
    }
    
    public void writeCategories(File file) throws IOException
    {
        syncAppsToCategoryFile();
        categoryFile.writeToFile(file);
        apps.syncOldApps();
    }
    
    public void writeCategories() throws IOException
    {
        syncAppsToCategoryFile();
        categoryFile.writeToFile();
        apps.syncOldApps();
    }
}
