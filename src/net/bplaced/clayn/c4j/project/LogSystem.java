package net.bplaced.clayn.c4j.project;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The LogSystem is used to give a simple way to create loggers which have a fix
 * parent loggers which can be configured to be filled with your custom
 * handlers. If you want a special handling for the logs created by classes in
 * Config4J this class gives you access to the parent Logger.
 *
 * @author Clayn
 * @since 0.1
 * @version 0.1
 */
public final class LogSystem
{

    //<editor-fold desc="Attribute">
    private static final Logger PARENT = Logger.getLogger(LogSystem.class.
            getName());
    private static Level oldLevel;
    //</editor-fold>

    //<editor-fold desc="Konstruktoren">
    /**
     * Prevent Instances of this class.
     */
    private LogSystem()
    {
        
    }
    //</editor-fold>
    //<editor-fold desc="Private">
    //</editor-fold>
    //<editor-fold desc="Public">
    /**
     * Returns the parent Logger for all Loggers used by Config4J. If you want
     * to setup your own handler for the logger in the Config4J library you can
     * attach them to this parent Logger.
     *
     * @return the parent Logger for all used loggers in Config4J
     * @since 0.1
     */
    public static Logger getParentLogger()
    {
        return PARENT;
    }

    /**
     * Returns a simple Logger for the given class, or in detail classname. The
     * returned logger will have the parent logger as its parent so all those
     * handlers will be informed if a logger is used. Also many configurations
     * such as Level and Filter are taken from the parent Logger.
     *
     * @param cl the class which requests a logger
     * @return a new logger with the specific class name as information
     * @since 0.1
     */
    public static Logger getLogger(Class cl)
    {
        Logger logger = Logger.getLogger(cl.getName());
        logger.setParent(PARENT);
        logger.setLevel(PARENT.getLevel());
        logger.setFilter(PARENT.getFilter());
        return logger;
    }

    /**
     * Sets wether Logger messages should be disabled or not. In fact this
     * method sets the Parent-Logger Level to {@link Level#OFF} and stores the
     * old Level. The old Level will be used after enableing the logging.
     *
     * @param dis {@code true} if logging should be disabled {@code false}
     * otherwise
     */
    public static void setDisabled(boolean dis)
    {
        if (dis)
        {
            oldLevel = PARENT.getLevel();
            PARENT.setLevel(Level.OFF);
        }
        else
        {
            PARENT.setLevel(oldLevel);
        }
    }
    //</editor-fold>
}
