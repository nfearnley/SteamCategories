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
public class CategoryListModel extends AbstractListModel<String>
{

    AppList apps;

    public CategoryListModel(AppList apps)
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
        int size = apps.getCategorySize();
        return size;
    }

    @Override
    public String getElementAt(int index)
    {
        String element = apps.getCategory(index);
        return element;
    }
}
