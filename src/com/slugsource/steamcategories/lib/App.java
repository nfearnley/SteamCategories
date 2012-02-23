/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
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
public class App
{

    private String name;
    private String appid;
    private String category;

    // TODO: Add javadocs
    public App(String name, String appid)
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
        final App other = (App) obj;
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
    
    public App clone()
    {
        App app = new App(name, appid);
        app.setCategory(category);
        return app;
    }
}
