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
    SteamCategories cats;
    
    public CategoryListModel(SteamCategories cats)
    {
        if (cats == null)
            throw new NullPointerException("Cats cannot be null.");
        this.cats = cats;
    }

    @Override
    public int getSize()
    {
        return cats.getCategorySize();
    }

    @Override
    public String getElementAt(int index)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
