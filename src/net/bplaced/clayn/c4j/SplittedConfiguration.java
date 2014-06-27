package net.bplaced.clayn.c4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import net.bplaced.clayn.c4j.io.ConfigFile;

/**
 * Subclass of {@link Configuration} which allows you to store different 
 * data into different files. This may become usefull if you want to store 
 * different types of settings at different locations. Also it may become usefull 
 * for an profilesystem where each user has his own settingsfile.
 *
 * @author Clayn
 * @see Configuration
 * @see SplittedEnvironment
 * @since 0.1
 * @version 0.1
 */
public class SplittedConfiguration extends Configuration implements Splitable
{
    //<editor-fold desc="Attribute">   
    //</editor-fold>

    //<editor-fold desc="Konstruktoren">
    /**
     * Creates a new, empty {@link SplittedConfiguration} without any values.
     *
     * @see #SplittedConfiguration(net.bplaced.clayn.c4j.Configuration)
     * @see #SplittedConfiguration(java.io.InputStream)
     * @since 0.1
     */
    public SplittedConfiguration()
    {
        super();
    }

    /**
     * Creates a new {@link SplittedConfiguration} and loads all the values
     * aviable from the given Inputstream. The loading is made with
     * {@link Properties#load(java.io.InputStream)} so you can read how the
     * format should be at the Java Language Specification.
     *
     * @param in the Inputstream to load the values from.
     * @throws IOException if an IOException occures during the loading.
     * @see #SplittedConfiguration()
     * @see #SplittedConfiguration(java.io.InputStream)
     * @since 0.1
     */
    public SplittedConfiguration(InputStream in) throws IOException
    {
        super(in);
    }

    /**
     * Creates a new {@link SplittedConfiguration} with all the values from the
     * given {@link Configuration}. This creates a flat copy of the given
     * {@link Configuration}.
     *
     * @param conf the Configuration to copy the values from.
     * @see #SplittedConfiguration()
     * @see #SplittedConfiguration(net.bplaced.clayn.c4j.Configuration)
     * @since 0.1
     */
    public SplittedConfiguration(Configuration conf)
    {
        this();
        for (String key : conf.properties.stringPropertyNames())
        {
            properties.setProperty(key, conf.properties.getProperty(key));
        }
    }

    //</editor-fold>
    //<editor-fold desc="Private">

    private Properties buildProperties(ConfigFile file)
    {

        Properties p = new Properties();
        if (file == null)
        {
            return p;
        }
        for (String key : properties.stringPropertyNames())
        {
            if (key.startsWith(file.getPraefix()))
            {
                p.setProperty(key, properties.getProperty(key));
            }
        }
        return p;
    }
    //</editor-fold>

    //<editor-fold desc="Public">
    /**
     * Loads all configurations from the given ConfigFiles into the
     * Configurations Properties. The praefixes of the ConfigFiles will be
     * ignored due to its only necessary for storing. This can become usefull if
     * you have different Files for different Configurations and want to bring
     * them all together. <br>
     * Note that you should make shure to have no multiple keys due they will be
     * overwritten.
     *
     * @param files the configfiles which should be loaded.
     * @throws IOException if an IOException occures at
     * {@link #load(java.io.InputStream)} or the file was not found
     * @see #load(java.io.InputStream)
     * @since 0.1
     */
    @Override
    public void load(ConfigFile... files) throws IOException
    {
        for (ConfigFile file : files)
        {
            File f = file.getConfigFile();
            try (InputStream in = new FileInputStream(f))
            {
                load(in);
            }
        }
    }

    /**
     * Stores all the saved values from this Configuration into the given Files.
     * A file will be filled with all the keys that start with the values and
     * keys that start with the given praefix from the associated
     * {@link ConfigFile}. If multiple {@link ConfigFile}s share the same
     * praefix all those files will be filled with the same key and value pairs.
     *
     * @param files the files were to store the configuration
     * @throws IOException in an IOException occures during the writing.
     * @since 0.1
     */
    @Override
    public void save(ConfigFile... files) throws IOException
    {
        for (ConfigFile file : files)
        {
            File f = file.getConfigFile();
            Properties p = buildProperties(file);
            try (OutputStream out = new FileOutputStream(f))
            {
                p.store(out, file.getComment());
                out.flush();
            }
        }
    }

    //</editor-fold>
}
