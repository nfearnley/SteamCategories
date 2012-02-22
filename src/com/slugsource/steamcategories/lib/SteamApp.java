/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author Nathan Fearnley
 */
public class SteamApp
{

    private String name;
    private String appid;

    // TODO: Add javadocs
    public SteamApp(String name, String appid)
    {
        if (name == null)
        {
            throw new NullPointerException("Name cannot be null.");
        }
        if (appid == null)
        {
            throw new NullPointerException("AppID cannot be null.");
        }
        this.name = name;
        this.appid = appid;

    }

    // TODO: Add javadocs
    public String getAppid()
    {
        return appid;
    }

    // TODO: Add javadocs
    public String getName()
    {
        return name;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final SteamApp other = (SteamApp) obj;
        if (!Objects.equals(this.appid, other.appid))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.appid);
        return hash;
    }

    // TODO: Fix Throws
    // TODO: Add javadocs
    public static SteamApp[] readFromUrl(String url) throws IOException
    {
        URL gamesUrl = new URL(url);

        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
        try
        {
            is = gamesUrl.openStream();
            isr = new InputStreamReader(is);
            in = new BufferedReader(isr);

            // TODO: Read XML
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = null;
            try
            {
                db = dbf.newDocumentBuilder();
            } catch (ParserConfigurationException e)
            {
                throw new IOException(e);
            }
            db.parse(in);
            
        } finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                } catch (IOException ex)
                {
                }
            }
        }
        // TODO: Return something useful
        return null;

    }

    // TODO: Fix Throws
    // TODO: Add javadocs
    public static SteamApp[] readFromSteamId(String steamId) throws IOException
    {
        final String urlPrefix = "http://steamcommunity.com/id/";
        final String urlSuffix = "/games?xml=1";

        String url = urlPrefix + steamId + urlSuffix;

        SteamApp[] result = null;

        
        result = readFromUrl(url);

        return result;
    }
}
