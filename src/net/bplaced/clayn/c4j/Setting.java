package net.bplaced.clayn.c4j;

import java.io.Serializable;
import java.util.Objects;
import net.bplaced.clayn.c4j.util.Parseable;

/**
 * Base class for all different Types of Settings. A Type of Settings describes
 * which type it should have for saving in attributes. Due to the values are
 * meant to be stored in a file (strings) a Setting is implementing
 * {@link Parseable} for {@code <T,String>}. Each setting can be identified with
 * a given key.
 *
 * @author Clayn
 * @param <T> The type of Objects that can be stored with the saved key
 * @since 0.1
 * @version 0.1
 */
public abstract class Setting<T> implements Parseable<T, String>, Serializable
{

    /**
     * The key for this setting which should distinct.
     * @since 0.1
     */
    protected final String settingsKey;

    /**
     * Creates a new Setting with the given key as identifier.
     *
     * @param key the key used for this Setting
     * @since 0.1
     */
    public Setting(String key)
    {
        settingsKey = key;
    }

    /**
     * Returns the key for this Setting
     *
     * @return the key for this Setting
     * @since 0.1
     */
    public final String getSettingsKey()
    {
        return settingsKey;
    }

    /**
     * Parses the given Object of the defined Type into an String. Simply calls
     * {@link Object#toString()} on the given Object. Subclasses may implement a
     * different behavior.<br><br>
     * <b>Documentation from {@link Parseable}:<br></b> {@inheritDoc }
     *
     * @param t the Object to be parsed
     * @return a string representation for the given Object. If a subclass does 
     * not overwrite this method it will return {@link Object#toString()} or 
     * {@code null} if the given Object was {@code null}.
     * @since 0.1
     */
    @Override
    public String parseTo(T t)
    {
        if(t==null)
            return null;
        return t.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj == null || !(obj instanceof Setting) ? false : settingsKey.
                equals(((Setting) obj).settingsKey);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.settingsKey);
        return hash;
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName()+": "+settingsKey;
    }

    
    
}
