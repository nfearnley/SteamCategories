/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.gui;

/**
 *
 * @author c260683
 */
public class NotValidFileException extends Exception
{

    /**
     * Creates a new instance of
     * <code>NotConfigFileException</code> without detail message.
     */
    public NotValidFileException()
    {
    }

    /**
     * Constructs an instance of
     * <code>NotConfigFileException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public NotValidFileException(String msg)
    {
        super(msg);
    }
}
