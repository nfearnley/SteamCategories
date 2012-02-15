/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib.vdf;

import com.slugsource.steamcategories.lib.NodeNameNotUniqueException;
import java.io.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nathan Fearnley
 */
public class Node implements NodeInterface
{
    private String name;

    public Node(String name)
    {
        this.name = name;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public String getValue()
    {
        return null;
    }

    @Override
    public void setValue(String value)
    {
        return;
    }

    @Override
    public Node getNode(String name)
    {
        return null;
    }

    @Override
    public void addNode(Node node) throws NodeNameNotUniqueException
    {
        return;
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
    
    @Override
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
                node = new ValueNode(name, value);
            }
            else if (parser.ttype == '{')
            {
                BranchNode branchNode = new BranchNode(name);
                while (parser.nextToken() != '}')
                {
                    parser.pushBack();
                    branchNode.addNode(Node.parse(parser));
                }
                node = branchNode;
            }
            
            return node;
        }
        catch (NodeNameNotUniqueException | IOException e)
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
    public String toString(int level)
    {
        return "";
    }
}
