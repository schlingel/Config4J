package net.bplaced.clayn.c4j.project;

/**
 * This interface only contains the versions of the different libraries that may 
 * be used by Config4J. Also it contains the current version of Config4J itself. 
 * This may become usefull if you want to create an updater for this library.
 * @author Clayn
 * @since 0.1
 * @version 0.1
 */
public interface Version 
{
    /**
     * Version of the current Config4J. May not be the latest.
     */
    public static final String CONFIG4J_VERSION="0.1";
}
