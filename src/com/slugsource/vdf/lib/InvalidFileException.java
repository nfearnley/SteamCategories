/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.vdf.lib;

/**
 *
 * @author Nathan Fearnley
 */
public class InvalidFileException extends Exception
{

    /**
     * Creates a new instance of
     * <code>InvalidFileException</code> without detail message.
     */
    public InvalidFileException()
    {
    }

    /**
     * Constructs an instance of
     * <code>InvalidFileException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidFileException(String msg)
    {
        super(msg);
    }
}
