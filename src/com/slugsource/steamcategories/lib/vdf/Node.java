/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib.vdf;

import java.io.*;
import java.util.LinkedList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Nathan Fearnley
 */
public class Node
{
    private String name;
    private String value = null;
    private LinkedList<Node> children = null;

    public Node(String name)
    {
        this.name = name;
    }
    
    public Node(String name, String value)
    {
        this(name);
        this.value = value;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public String getValue()
    {
        return this.value;
    }

    public void setValue(String[] path, String name, String value)
    {
        Node node = this;
        for (String nodeName : path)
        {
           Node childNode = node.getNode(nodeName);
            if (childNode == null)
            {
                childNode = new Node(nodeName);
                node.addNode(childNode);
            }
            node = childNode;
        }
        
        Node valNode = node.getNode(name);
        if (valNode == null)
        {
            valNode = new Node(name, value);
            node.addNode(valNode);
        }
        else
        {
            valNode.setValue(value);
        }
    }
    
    public void setValue(String value)
    {
        this.value = value;
        children = null;
    }

    public Node getNode(String name)
    {
        if (children == null)
            return null;
        
        int index = children.indexOf(new Node(name));

        Node result = null;
        if (index != -1)
        {
            result = children.get(index);
        }
        return result;
    }

    public void addNode(Node node)
    {
        if (children == null)
        {
            children = new LinkedList<Node>();
            value = null;
        }
        children.add(node);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Node)) {
            return false;
        }
        final Node other = (Node) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.name);
        return hash;
    }
    
    public static Node readFromFile(File file)
    {
        FileInputStream fis = null;
        Node result = null;
        try
        {   
            fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            Reader r = new BufferedReader(isr);
            result = Node.parse(r);
        } catch (FileNotFoundException e)
        {
            return null;
        }
        finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                } catch (IOException ex)
                {

                }
            }
        }
        return result;
    }
    
    public static void writeToFile(File file, Node node)
    {
        FileOutputStream fos = null;
        OutputStreamWriter osw;
        BufferedWriter w;
        
        try
        {
            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos);
            w = new BufferedWriter(osw);
            w.write(node.toString());
            w.flush();
        } catch (IOException e)
        {
            
        }
        finally
        {
            if (fos != null)
            {
                try
                {
                    fos.close();
                } catch (IOException ex)
                {

                }
            }
        }
    }
    
    public void writeToFile(File file) throws IOException
    {
        writeToFile(file, this);
    }
    
    public static Node parse(Reader r)
    {
        StreamTokenizer parser = getParser(r);
        return parse(parser);
    }
    
    private static Node parse(StreamTokenizer parser)
    {
        Node node = null;
        try
        {
            // Read top node name
            parser.nextToken();
            String name = parser.sval;
            
            parser.nextToken();
            if (parser.sval != null)
            {
                String value = parser.sval;
                node = new Node(name, value);
            }
            else if (parser.ttype == '{')
            {
                Node branchNode = new Node(name);
                while (parser.nextToken() != '}')
                {
                    parser.pushBack();
                    branchNode.addNode(Node.parse(parser));
                }
                node = branchNode;
            }
            
            return node;
        }
        catch (IOException e)
        {
            return null;
        }
    }
    
    private static StreamTokenizer getParser(Reader r)
    {
        StreamTokenizer parser = new StreamTokenizer(r);
        parser.resetSyntax();
        parser.eolIsSignificant(false);
        parser.lowerCaseMode(false);
        parser.slashSlashComments(true);
        parser.slashStarComments(false);
        parser.commentChar('/');
        parser.quoteChar('"');
        parser.whitespaceChars('\u0000', '\u0020');
        parser.wordChars('A', 'Z');
        parser.wordChars('a', 'z');
        parser.wordChars('\u00A0', '\u00FF');
        return parser;
    }
    
    @Override
    public String toString()
    {
        return toString(0);
    }
    
    public String toString(int level)
    {
        String output;
        if (value != null)
        {
            String name = '"' + StringEscapeUtils.escapeJava(this.getName()) + '"';
            String value = '"' + StringEscapeUtils.escapeJava(this.getValue()) + '"';
            output = StringUtils.repeat('\t', level) + name + "\t\t" + value + '\n';
        }
        else
        {
            String name = '"' + StringEscapeUtils.escapeJava(getName()) + '"';

            StringBuilder childrenOutput = new StringBuilder();
            for (Node child : children)
            {
                childrenOutput.append(child.toString(level + 1));
            }

            output = StringUtils.repeat('\t', level) + name + '\n';
            output += StringUtils.repeat('\t', level) + "{\n";
            output += childrenOutput;
            output += StringUtils.repeat('\t', level) + "}\n";
        }
        return output;
    }
}
