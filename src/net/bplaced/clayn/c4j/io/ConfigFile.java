package net.bplaced.clayn.c4j.io;

import java.io.File;
import java.util.Objects;
import net.bplaced.clayn.c4j.Configuration;
import net.bplaced.clayn.c4j.Environment;

/**
 * A class used to filter what kind of keys from a {@link Configuration} or
 * {@link Environment} should be written in a specific file. Additional you can
 * add a comment to each {@link ConfigFile} which is only used by
 * {@link Configuration} so its not necessary.
 *
 *
 * @author Clayn
 * @since 0.1
 * @version 0.1
 */
public final class ConfigFile
{

    //<editor-fold desc="Attribute">
    private final String praefix;
    private final File configFile;
    private final String comment;

    //</editor-fold>
    //<editor-fold desc="Konstruktoren">
    /**
     * Creates a new ConfigFile for the given praefix and the given file without
     * any comment.
     *
     * @param praefix the praefix for the keys that should be stored in the
     * given file
     * @param configFile the file where to store the <key,value> pairs with the
     * given praefix
     * @see #ConfigFile(java.lang.String, java.io.File, java.lang.String)
     * @since 0.1
     */
    public ConfigFile(String praefix, File configFile)
    {
        this(praefix, configFile, null);
    }

    /**
     * Creates a new ConfigFile for the given praefix, given file and given
     * comment.
     *
     * @param praefix the praefix for the keys that should be stored in the
     * given file
     * @param configFile the file where to store the <key,value> pairs with the
     * given praefix
     * @param comment the comment that should be used in the file
     * @see #ConfigFile(java.lang.String, java.io.File)
     * @since 0.1
     * @throws IllegalArgumentException if {@code praefix} or {@code configFile}
     * are {@code null}
     */
    public ConfigFile(String praefix, File configFile, String comment)
    {
        if (praefix == null || configFile == null)
        {
            throw new IllegalArgumentException(
                    "Praefix and configFile must not be 'null'.");
        }

        this.praefix = praefix;
        this.configFile = configFile;
        this.comment = comment == null ? "" : comment;
    }

    //</editor-fold>
    //</editor-fold>
    //<editor-fold desc="Private">
    //</editor-fold>
    //<editor-fold desc="Public">
    /**
     * Returns the File where to store the key,value pairs where the keys start
     * with a specific praefix.
     *
     * @return the file to store the key,value pairs
     * @since 0.1
     */
    public File getConfigFile()
    {
        return configFile;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.praefix);
        hash = 67 * hash + Objects.hashCode(this.configFile);
        return hash;
    }

    /**
     * Returns the stored comment for this ConfigFile. The returned value is
     * never {@code null}.
     *
     * @return the comment stored in this ConfigFile
     */
    public String getComment()
    {
        return comment;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || !(obj instanceof ConfigFile))
        {
            return false;
        }
        ConfigFile cnfg = ConfigFile.class.cast(obj);
        return praefix.equals(cnfg.getPraefix()) && configFile.equals(cnfg.
                getConfigFile());
    }

    /**
     * Returns the praefix for the keys that should be stored with this
     * ConfigFile.
     *
     * @return the praefix for the keys for this ConfigFile
     * @since 0.1
     */
    public String getPraefix()
    {
        return praefix;
    }

    @Override
    public String toString()
    {
        return new StringBuilder().append("[Praefix: ").append(praefix).
                append("; File: ").
                append(configFile).append("]").toString();
    }

}
