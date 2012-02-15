/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib.vdf;

import com.slugsource.steamcategories.lib.NodeNameNotUniqueException;
import java.util.ArrayList;

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
}
