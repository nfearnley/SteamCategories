/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib.vdf;

import com.slugsource.steamcategories.lib.NodeNameNotUniqueException;
import java.util.ArrayList;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Nathan Fearnley
 */
public class BranchNode extends Node
{

    private ArrayList<Node> children = new ArrayList<>();

    // Create a new node of this name
    public BranchNode(String name)
    {
        super(name);
    }

    // Add a child node
    // throw NodeNameNotUniqueException if a node of the same name exists
    public void addNode(Node node) throws NodeNameNotUniqueException
    {
        if (children.contains(node))
        {
            throw new NodeNameNotUniqueException();
        }
        children.add(node);
    }

    // Get a child node by name
    // return null if not found
    public Node getNode(String name)
    {
        int index = children.indexOf(new Node(name));

        Node result = null;
        if (index != -1)
        {
            result = children.get(index);
        }
        return result;
    }

    public String toString()
    {
        return toString(0);
    }

    public String toString(int level)
    {
        String name = '"' + StringEscapeUtils.escapeJava(getName()) + '"';

        StringBuilder childrenOutput = new StringBuilder();
        for (Node child : children)
        {
            childrenOutput.append(child.toString(level + 1));
        }
        
        String output = StringUtils.repeat('\t', level) + name + '\n';
        output += StringUtils.repeat('\t', level) + "{\n";
        output += childrenOutput;
        output += StringUtils.repeat('\t', level) + "}\n";
        
        return output;

    }
}
