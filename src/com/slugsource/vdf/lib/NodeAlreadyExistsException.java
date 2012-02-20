/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.vdf.lib;

/**
 *
 * @author Nathan Fearnley
 */
public class NodeAlreadyExistsException extends Exception
{

    /**
     * Creates a new instance of
     * <code>NodeAlreadyExistsException</code> without detail message.
     */
    public NodeAlreadyExistsException()
    {
    }

    /**
     * Constructs an instance of
     * <code>NodeAlreadyExistsException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public NodeAlreadyExistsException(String msg)
    {
        super(msg);
    }
}
