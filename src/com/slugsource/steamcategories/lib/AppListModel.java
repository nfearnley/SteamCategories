/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib;

import javax.swing.AbstractListModel;

/**
 *
 * @author Nathan Fearnley
 */
public class AppListModel extends AbstractListModel<String>
{

    AppList apps;

    public AppListModel(AppList apps)
    {
        if (apps == null)
        {
            throw new NullPointerException("Cats cannot be null.");
        }
        this.apps = apps;
    }

    @Override
    public int getSize()
    {
        int size = apps.getAppSize();
        return size;
    }

    @Override
    public String getElementAt(int index)
    {
        String element = apps.getName(index);
        return element;
    }
}
