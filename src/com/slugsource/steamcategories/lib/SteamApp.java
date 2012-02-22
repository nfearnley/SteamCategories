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
import java.util.List;
import java.util.LinkedList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class SteamApp
{

    private String name;
    private String appid;
    private String category;

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

    // TODO: Add javadocs
    public String getCategory()
    {
        return category;
    }

    // TODO: Add javadocs
    public void setCategory(String category)
    {
        this.category = category;
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

    private static List<SteamApp> readGamesFromXml(Element doc) throws IOException
    {
        List<SteamApp> gamesList = new LinkedList<SteamApp>();
        NodeList games = doc.getElementsByTagName("game");
        assert (games != null);

        for (int x = 0; x < games.getLength(); x++)
        {
            SteamApp game = readGameFromXml(doc);
            gamesList.add(game);
        }

        return gamesList;
    }

    // TODO: Add javadocs
    private static List<SteamApp> readGamesFromUrl(String url) throws IOException
    {

        Element doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        
        Document gamesXml;
        
        try
        {
            DocumentBuilder db = dbf.newDocumentBuilder();
            gamesXml = db.parse(url);
        } catch (ParserConfigurationException | SAXException ex)
        {
            throw new IOException(ex);
        }

        doc = gamesXml.getDocumentElement();

        List<SteamApp> gamesList = readGamesFromXml(doc);
        return gamesList;
    }

    // TODO: Add javadocs
    public static List<SteamApp> readGamesFromSteamId(String steamId) throws IOException
    {
        final String urlPrefix = "http://steamcommunity.com/id/";
        final String urlSuffix = "/games?xml=1";

        String url = urlPrefix + steamId + urlSuffix;

        List<SteamApp> gamesList = readGamesFromUrl(url);

        return gamesList;
    }

    // TODO: Add javadocs
    private static SteamApp readGameFromXml(Element element) throws IOException
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

        SteamApp app = new SteamApp(name, appId);
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
}
