/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib.vdf;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

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
    
    public String toString()
    {
        return toString(0);
    }
    
    public String toString(int level)
    {
        String name = '"' + StringEscapeUtils.escapeJava(this.getName()) + '"';
        String value = '"' + StringEscapeUtils.escapeJava(this.getValue()) + '"';
        return StringUtils.repeat('\t', level) + name + "\t\t" + value + '\n';
    }
}
