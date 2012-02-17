package com.slugsource.vdf.lib;

import java.io.*;
import java.util.LinkedList;
import java.util.Objects;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * A node object that can hold either a value, or child node objects
 *
 * @author Nathan Fearnley
 */
public class Node
{

    private String name;
    private String value = null;
    private LinkedList<Node> children = null;

    /**
     * Constructor that sets the name of the node
     *
     * @param name name of the node
     */
    public Node(String name)
    {
        this.name = name;
    }

    /**
     * Constructor that sets the name and value of the node
     *
     * @param name name of the node
     * @param value value of the node
     */
    public Node(String name, String value)
    {
        this(name);
        this.value = value;
    }

    /**
     * Get the name of the node
     *
     * @return Name of the node
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Get the value of the node
     *
     * @return Value of the node
     */
    public String getValue()
    {
        return this.value;
    }

    /**
     * Set the value of a particular subnode
     *
     * @param path Array of node path that subnode is contained in
     * @param name Name of subnode to set
     * @param value Value to set subnode to
     */
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
        } else
        {
            valNode.setValue(value);
        }
    }

    /**
     * Sets the value for this node. Will delete all children.
     *
     * @param value Value to set node to
     */
    public void setValue(String value)
    {
        this.value = value;
        children = null;
    }

    /**
     * Get a particular subnode
     * @param path  Array of node path that subnode is contained in
     * @param name  Name of subnode to get
     * @return  Subnode if found, null if not found.
     */
    public Node getNode(String[] path, String name)
    {
        Node node = this;

        for (String nodeName : path)
        {
            Node childNode = node.getNode(nodeName);
            if (childNode == null)
            {
                return null;
            }
            node = childNode;
        }
        return node;
    }
    
    /**
     * Lookup a child node by name.
     *
     * @param name Name of child node.
     * @return Node if found, null if not found.
     */
    public Node getNode(String name)
    {
        if (children == null)
        {
            return null;
        }

        int index = children.indexOf(new Node(name));

        Node result = null;
        if (index != -1)
        {
            result = children.get(index);
        }
        return result;
    }

    /**
     * Add a child node to this node. Will delete node value. Will fail silently
     * if node already exists.
     *
     * @param node Child node to add
     */
    public void addNode(Node node)
    {
        if (children == null)
        {
            children = new LinkedList<Node>();
            value = null;
        }
        if (!children.contains(node))
        {
            children.add(node);
        }
    }

    /**
     * Delete a child node from this node;
     *
     * @param node Child node to delete;
     */
    public void delNode(Node node)
    {
        if (children == null)
        {
            return;
        }
        children.remove(node);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Node))
        {
            return false;
        }
        final Node other = (Node) obj;
        if (!Objects.equals(this.name, other.name))
        {
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

    /**
     * Read a node from a configuration file.
     *
     * @param file Configuration file to read from
     * @return Node that was read. Null if process failed.
     */
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
        } finally
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

    /**
     * Write a node to a configuration file. Overwrites existing file. Will fail
     * silently.
     *
     * @param file File to write to.
     * @param node Node to save.
     */
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
        } finally
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

    /**
     * Write this node to a configuration file. Overwrites existing file. Will
     * fail silently.
     *
     * @param file File to write to
     */
    public void writeToFile(File file)
    {
        writeToFile(file, this);
    }

    /**
     * Read a configuration file from a reader and return the node.
     *
     * @param r The reader to read from
     * @return The node read
     */
    public static Node parse(Reader r)
    {
        StreamTokenizer parser = getParser(r);
        return parse(parser);
    }

    /**
     * Read a configuration file using provided parser and return the node.
     *
     * @param parser The parser used to read
     * @return The node read
     */
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
            } else if (parser.ttype == '{')
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
        } catch (IOException e)
        {
            return null;
        }
    }

    /**
     * Build a StreamTokenizer for parsing vdf files.
     *
     * @param r A reader to read from.
     * @return The StreamTokenizer to parse the given reader file.
     */
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

    /**
     * Returns a string representing this node and subnodes. Formatted for
     * output to file.
     *
     * @param level The level of indentation to return
     * @return String formatted for output to file.
     */
    public String toString(int level)
    {
        String output;
        if (value != null)
        {
            String name = '"' + StringEscapeUtils.escapeJava(this.getName()) + '"';
            String value = '"' + StringEscapeUtils.escapeJava(this.getValue()) + '"';
            output = StringUtils.repeat('\t', level) + name + "\t\t" + value + '\n';
        } else
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
