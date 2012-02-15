/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib.vdf;

/**
 *
 * @author Nathan Fearnley
 */
public class ValueNode extends Node
{
    private String value;

    public ValueNode(String name)
    {
        super(name);
        this.value = "";
    }
    
    public ValueNode(String name, String value)
    {
        super(name);
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}
