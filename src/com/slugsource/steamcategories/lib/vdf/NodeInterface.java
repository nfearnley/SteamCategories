/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slugsource.steamcategories.lib.vdf;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Nathan Fearnley
 */
public interface NodeInterface
{
    String getName();
    
    String getValue();
    
    void setValue(String value);
    
    Node getNode(String name);

    String toString(int level);
            
    void writeToFile(File file) throws IOException;
}
