/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib.vdf;

import java.io.File;
import java.io.Reader;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Nathan Fearnley
 */
public class NodeTest
{
    
    public NodeTest()
    {
    }

    /**
     * Test of getName method, of class Node.
     */
    @Test
    public void testGetName()
    {
        Node instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Node.
     */
    @Test
    public void testEquals()
    {
        Object obj = null;
        Node instance = null;
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class Node.
     */
    @Test
    public void testHashCode()
    {
        Node instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readFromFile method, of class Node.
     */
    @Test
    public void testReadFromFile() throws Exception
    {
        File file = new File("F:\\Programming\\SteamCategories\\sharedconfig.vdf");
        Node.readFromFile(file);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeToFile method, of class Node.
     */
    @Test
    public void testWriteToFile_File_Node() throws Exception
    {
        File file = null;
        Node node = null;
        Node.writeToFile(file, node);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeToFile method, of class Node.
     */
    @Test
    public void testWriteToFile_File() throws Exception
    {
        File file = null;
        Node instance = null;
        instance.writeToFile(file);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of parse method, of class Node.
     */
    @Test
    public void testParse()
    {
        Reader r = null;
        Node expResult = null;
        Node result = Node.parse(r);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Node.
     */
    @Test
    public void testToString()
    {
        int level = 0;
        Node instance = null;
        String expResult = "";
        String result = instance.toString(level);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
