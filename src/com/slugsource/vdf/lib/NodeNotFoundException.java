/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.vdf.lib;

/**
 *
 * @author Nathan Fearnley
 */
public class NodeNotFoundException extends Exception
{

    /**
     * Creates a new instance of
     * <code>NodeNotFoundException</code> without detail message.
     */
    public NodeNotFoundException()
    {
    }

    /**
     * Constructs an instance of
     * <code>NodeNotFoundException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public NodeNotFoundException(String msg)
    {
        super(msg);
    }
}
