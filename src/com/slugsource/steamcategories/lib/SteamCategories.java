/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib;

import com.slugsource.vdf.lib.InvalidFileException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.ListModel;

/**
 *
 * @author c260683
 */
public class SteamCategories
{

    private CategoryFile categoryFile = null;
    private AppList apps = null;

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

        this.categoryFile = new CategoryFile(file);
        this.apps = new AppList(steamId);
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

        AppList apps = new AppList(steamId);
        readApps();

    }

    public void readApps() throws IOException
    {
        apps.readAppsFromSteamId();
        syncCategoryFileToApps();
    }
    
    public List<String> getAppIdList()
    {
        return apps.getAppIdList();
    }
    
    public String getAppId(int index)
    {
        return apps.getAppId(index);
    }
    
    public List<String> getCategoryList()
    {
        return apps.getCategoryList();
    }
    
    public String getCategory(int index)
    {
        return apps.getCategory(index);
    }

    public void readCategories(File file) throws InvalidFileException, IOException
    {
        categoryFile = new CategoryFile(file);
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
    
    public ListModel getAppListModel()
    {
        return new AppListModel(apps);
    }
    
    public ListModel getCategoryListModel()
    {
        return new CategoryListModel(apps);
    }
}
