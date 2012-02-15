/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib.vdf;

import com.slugsource.steamcategories.lib.vdf.ValueNode;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Nathan Fearnley
 */
public class Tree
{
    Node rootNode;
    
    ArrayList<String> VdfValues;
    
    private void readFromFile(File file)
    {
        try
        {
            FileInputStream fis = new FileInputStream(file);
            Scanner scanner = new Scanner(fis);
            while (scanner.hasNextLine())
            {
            }
            
        }
        catch (FileNotFoundException ex)
        {

        }
    }
    
    private void writeToFile(File file)
    {
        
    }
    
    private StreamTokenizer getParser()
    {
        
    }
    
    private Node parseLine(String line, BranchNode currentNode)
    {   
        String name;
        String value;
        Node result;
        
        Pattern valueNodePattern = Pattern.compile("\\t*[^\\\\]\"(?<name>.*)[^\\\\]\"");
        Pattern branchNodePattern = Pattern.compile("\\t*[^\\\\]\"(?<name>.*)[^\\\\]\"\\t\\t[^\\\\]\"(?<value>.*)[^\\\\]\"");
        Matcher valueNodeMatcher = valueNodePattern.matcher(line);
        Matcher branchNodeMatcher = branchNodePattern.matcher(line);
        
        if (valueNodeMatcher.matches())
        {
            name = valueNodeMatcher.group("name");
            value = valueNodeMatcher.group("value");
            result = new ValueNode(name, value);
        }
        else if (branchNodeMatcher.matches())
        {
            name = branchNodeMatcher.group("name");
            result = new BranchNode(name);
        }
        else
        {
            result = null;
        }
        
        return result;
    }
}
