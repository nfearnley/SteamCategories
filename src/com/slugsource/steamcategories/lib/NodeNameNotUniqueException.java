/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib;

/**
 *
 * @author Nathan Fearnley
 */
public class NodeNameNotUniqueException extends Exception
{
    public NodeNameNotUniqueException()
    {
        super("Node name is not unique.");
    }
}
