package net.bplaced.clayn.c4j;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.bplaced.clayn.c4j.io.ConfigFile;

/**
 * Sublcass of {@link Environment} which enables, due to implementation of
 * {@link Splitable}, to store different Objects in different Files. Such as the
 * base {@link Environment} this class can store all types of Object but can
 * only save those Objects which implement {@link Serializable}. With this in
 * mind it can happen that some of the generated files may seem empty.
 *
 * @author Clayn
 * @since 0.1
 * @version 0.1
 * @see Environment
 * @see SplittedConfiguration
 */
public class SplittedEnvironment extends Environment implements Splitable
{
    //<editor-fold desc="Attribute">

    //</editor-fold>
    //<editor-fold desc="Konstruktoren">
    /**
     * Creates a new, empty {@link SplittedEnvironment} without any data.
     *
     * @see Environment#Environment()
     */
    public SplittedEnvironment()
    {
        super();
    }

    /**
     * Creates a new {@link SplittedEnvironment} and loads all informations from
     * the given Inputstream. It is recommended that the InputStream is at the
     * beginning due to the fact that the {@link #load(java.io.InputStream)}
     * method (which is called within the constructor) will try to read the
     * amount of stored Object which should be at the beginning of the stream.
     *
     * @param in the InputStream to read the data from
     * @throws IOException if an IOException occures during the loading.
     * @see #SplittedEnvironment()
     * @see Environment#Environment(java.io.InputStream)
     * @see #SplittedEnvironment(net.bplaced.clayn.c4j.Environment)
     * @since 0.1
     */
    public SplittedEnvironment(InputStream in) throws IOException
    {
        super(in);
    }

    /**
     * Creates a new {@link SplittedEnvironment} with all the informations from
     * the given {@link Environment}. This constructor creates a flat copy,
     * which means that manipulation at the original {@link Environment} will
     * not affect the new one.
     *
     * @param env the {@link Environment} to get the data from.
     * @see #SplittedEnvironment()
     * @see #SplittedEnvironment(java.io.InputStream)
     * @since 0.1
     */
    public SplittedEnvironment(Environment env)
    {
        this();
        for (String key : env.environment.keySet())
        {
            put(key, env.get(key));
        }
    }
    //</editor-fold>

    //<editor-fold desc="Private">
    /**
     * Creates a list with all Objects which should be stored in the given
     * ConfigFile. Only Objects implementing {@link Serializable} will be added
     * to the list.
     *
     * @param file
     * @return a list of the Objects to store
     */
    private List<Serializable> buildStoreList(ConfigFile file)
    {
        List<Serializable> back = new ArrayList<>();
        for (String key : environment.keySet())
        {
            if (key.startsWith(file.getPraefix()) && (environment.get(key) instanceof Serializable))
            {
                back.add((Serializable) environment.get(key));
            }
        }
        return back;
    }

    /**
     * Stores a list of {@link Serializable} objects into the given
     * OutputStream. At first the number, in other words {@code list.size()},
     * will be written into the stream to identify the amount of stored objects.
     * Used for correct reading.
     *
     * @param list
     * @param out
     * @throws IOException
     */
    private void storeList(List<Serializable> list, ObjectOutputStream out)
            throws
            IOException
    {
        out.writeInt(list.size());
        for (Serializable serializable : list)
        {
            out.writeObject(serializable);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Public">
    //</editor-fold>
    /**
     * Loads all stored values from all the given files and stores them under 
     * the specific key. Other than the {@link #save(net.bplaced.clayn.c4j.io.ConfigFile[])} 
     * method the key from the ConfigFiles will be ignored. But the order of the 
     * ConfigFiles is important due to the fact that the old values will be 
     * overwritten if a key occures more than one time. 
     * @param files the files which should be loaded.
     * @throws IOException if an IOException occures during the loading.
     * @see #save(net.bplaced.clayn.c4j.io.ConfigFile[])
     * @since 0.1
     */
    @Override
    public void load(ConfigFile... files) throws IOException
    {
        for (ConfigFile configFile : files)
        {
            try (InputStream in = new BufferedInputStream(new FileInputStream(
                    configFile.getConfigFile())))
            {
                load(in);
            }
        }
    }

    /**
     * Stores all the values in the specific File for the selected keys. Each 
     * ConfigFile defines which key,value pairs should be stored in a specific 
     * file. If the requested keys are very abstract the files may contain the 
     * same keys and values. Even if no keys where found for a ConfigFile the 
     * requested File will be created. The way the values will be stored is the 
     * same as {@link #save(java.io.OutputStream)}. 
     * @param files the files that should contain the specific key,value pairs.
     * @throws IOException if an IOException occures during the writing.
     * @see #load(net.bplaced.clayn.c4j.io.ConfigFile[]).
     * @since 0.1
     */
    @Override
    public void save(ConfigFile... files) throws IOException
    {
        for (ConfigFile cf : files)
        {
            try (ObjectOutputStream out = new ObjectOutputStream(
                    new BufferedOutputStream(new FileOutputStream(cf.
                                    getConfigFile()))))
            {
                storeList(buildStoreList(cf), out);
                out.flush();
                            }
        }
    }
}
