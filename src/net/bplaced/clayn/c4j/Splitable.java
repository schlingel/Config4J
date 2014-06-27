package net.bplaced.clayn.c4j;

import java.io.IOException;
import net.bplaced.clayn.c4j.io.ConfigFile;

/**
 * This interface defines that implementing classes will be able to store and 
 * load theire informations into and from different files depending on the 
 * key information in the arguments. The exact way how this is done should be 
 * read from the implementing Classes.
 * @author Clayn
 * @since 0.1
 * @version 0.1
 */
public interface Splitable 
{
    /**
     * Loads all informations from the given Files and stores them. It is not 
     * defined in which way or order the informations are loaded so you should 
     * read the specific documentation.
     * @param files the files where to load the informations from
     * @throws IOException if an IOException occures during the loading
     * @see #save(net.bplaced.clayn.c4j.io.ConfigFile[]) 
     * @since 0.1
     */
    public void load(ConfigFile... files) throws IOException;
    
    /**
     * Stores all key,value pairs into the given files if the keys for those values 
     * start with the key for the specific file. How the saving will be made 
     * should be well documented by the implementator. Also it is not necessary 
     * that the key must start with the requested one.
     * @param files the Files to store the informations to.
     * @throws IOException if an IOException occures during the writing.
     * @see #load(net.bplaced.clayn.c4j.io.ConfigFile[])
     * @since 0.1
     */
    public void save(ConfigFile... files) throws IOException;
}
