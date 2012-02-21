/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib;

import java.util.Objects;

/**
 *
 * @author Nathan Fearnley
 */
public class SteamApp
{

    private String name;
    private String appid;

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

    public String getAppid()
    {
        return appid;
    }

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
}
