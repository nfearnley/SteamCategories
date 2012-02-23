/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Nathan Fearnley
 */
public class AppList
{

    private String steamId;
    private HashMap<String, App> apps;
    private HashMap<String, App> oldApps;
    private Set<String> categories;

    public AppList()
    {
    }

    public AppList(String steamId)
    {
        this.steamId = steamId;
    }

    /**
     * Returns whether or not this file has changed since last save
     *
     * @return True if file has changed since last save, false if it has not
     * changed.
     */
    public boolean isDirty()
    {
        if (apps == null)
        {
            return false;
        }
        boolean dirty = false;
        for (String appId : apps.keySet())
        {
            dirty = isDirty();
            if (dirty)
            {
                break;
            }
        }
        return dirty;
    }

    public boolean isDirty(String appId)
    {
        App app = apps.get(appId);
        App oldApp = oldApps.get(appId);

        String category = app.getCategory();
        String oldCategory = oldApp.getCategory();

        boolean result = false;
        if (category == null && oldCategory == null)
        {
            result = false;
        } else
        {
            result = !category.equals(oldCategory);
        }
        return result;
    }

    public Set<String> getAppIdList()
    {
        return apps.keySet();
    }
    
    public Set<String> getCategoryList()
    {
        return categories;
    }

    public int getAppSize()
    {
        return apps.size();
    }
    
    public int getCategorySize()
    {
        return categories.size();
    }

    public String getCategory(String appId)
    {
        if (apps == null)
        {
            throw new IllegalStateException("apps must be loaded before getting an app category.");
        }
        if (appId == null)
        {
            throw new NullPointerException("AppId cannot be null.");
        }

        App app = apps.get(appId);
        String category = null;
        if (app != null)
        {
            category = app.getCategory();
        }

        return category;
    }

    public String getName(String appId)
    {
        if (apps == null)
        {
            throw new IllegalStateException("apps must be loaded before getting an app category.");
        }
        if (appId == null)
        {
            throw new NullPointerException("AppId cannot be null.");
        }

        App app = apps.get(appId);
        String name = app.getName();
        
        return name;
    }

    public boolean setCategory(String appId, String category)
    {
        if (apps == null)
        {
            throw new IllegalStateException("apps must be loaded before setting an app category.");
        }
        if (appId == null)
        {
            throw new NullPointerException("AppId cannot be null.");
        }

        boolean result = false;
        App app = apps.get(appId);
        if (app != null)
        {
            app.setCategory(category);

            if (category != null)
            {
                categories.add(category);
            }
            result = true;
        }
        return result;
    }

    public void readAppsFromSteamId(String steamId) throws IOException
    {
        this.steamId = steamId;
        readAppsFromSteamId();
    }

    public void readAppsFromSteamId() throws IOException
    {
        if (steamId == null)
        {
            throw new IllegalStateException("SteamId must be set before calling readAppsFromSteamId().");
        }
        this.apps = getAppsFromSteamId(steamId);
        categories = new HashSet<String>();
        syncOldApps();
    }

    // TODO: Add javadocs
    private static HashMap<String, App> getAppsFromSteamId(String steamId) throws IOException
    {
        final String urlPrefix = "http://steamcommunity.com/id/";
        final String urlSuffix = "/games?xml=1";

        String url = urlPrefix + steamId + urlSuffix;

        HashMap<String, App> appsList = getAppsFromUrl(url);

        return appsList;
    }

    // TODO: Add javadocs
    private static HashMap<String, App> getAppsFromUrl(String url) throws IOException
    {

        Element docElem = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        Document doc;

        try
        {
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(url);
        } catch (ParserConfigurationException | SAXException ex)
        {
            throw new IOException(ex);
        }

        docElem = doc.getDocumentElement();

        HashMap<String, App> appsList = getAppsFromXml(docElem);
        return appsList;
    }

    private static HashMap<String, App> getAppsFromXml(Element doc) throws IOException
    {
        HashMap<String, App> appsList = new HashMap<String, App>();
        NodeList nl = doc.getElementsByTagName("game");
        assert (nl != null);

        for (int x = 0; x < nl.getLength(); x++)
        {
            App app = getAppFromXml((Element) nl.item(x));
            appsList.put(app.getAppid(), app);
        }

        return appsList;
    }

    // TODO: Add javadocs
    private static App getAppFromXml(Element element) throws IOException
    {
        if (element == null)
        {
            throw new NullPointerException("Element cannot be null.");
        }

        String name = getTextValue(element, "name");
        String appId = getTextValue(element, "appID");

        if (name == null)
        {
            throw new IOException("Could not read app element from xml.");
        }
        if (appId == null)
        {
            throw new IOException("Could not read app element from xml.");
        }

        App app = new App(name, appId);
        return app;
    }

    // TODO: Add javadocs
    /**
     * I take a xml element and the tag name, look for the tag and get the text
     * content i.e for <employee><name>John</name></employee> xml snippet if the
     * Element points to employee node and tagName is 'name' I will return John
     */
    private static String getTextValue(Element element, String tagName)
    {
        if (element == null)
        {
            throw new NullPointerException("Element cannot be null.");
        }

        if (tagName == null)
        {
            throw new NullPointerException("TagName cannot be null.");
        }

        String textVal = null;
        NodeList nl = element.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0)
        {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }

        return textVal;
    }

    private static HashMap<String, App> cloneList(HashMap<String, App> list)
    {
        HashMap<String, App> result = new HashMap<String, App>();
        for (String key : list.keySet())
        {
            App app = list.get(key);
            result.put(key, app.clone());
        }
        return result;
    }

    public void syncOldApps()
    {
        oldApps = cloneList(apps);
    }
}
