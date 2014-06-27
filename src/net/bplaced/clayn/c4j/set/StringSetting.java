package net.bplaced.clayn.c4j.set;

import net.bplaced.clayn.c4j.Setting;

/**
 * Implementation of the {@link Setting} class for {@code String} values.
 *
 * @author Clayn
 * @since 0.1
 * @version 0.1
 */
public class StringSetting extends Setting<String>
{

    //<editor-fold desc="Attribute">
    //</editor-fold>
    //<editor-fold desc="Konstruktoren">
    /**
     * Constructs the {@link StringSetting} for the given key.
     * @param key this setting is used for.
     * @see Setting#Setting(java.lang.String) 
     * @since 0.1
     */
    public StringSetting(String key)
    {
        super(key);
    }

    //</editor-fold>
    //<editor-fold desc="Private">
    //</editor-fold>
    //<editor-fold desc="Public">
    /**
     * Simply returns the given String. Overrites {@link Setting#parseTo(java.lang.Object)} 
     * to prevent a unneaded method call.
     * @param t {@inheritDoc }
     * @return the given String itself
     * @see Setting#parseTo(java.lang.Object) 
     * @since 0.1
     */
    @Override
    public String parseTo(String t)
    {
        return t;
    }

    /**
     * Parses a String from the given one. Just as {@link #parseTo(java.lang.String)} 
     * it simply returns the given String.
     * @param u {@inheritDoc }
     * @return the given String
     * @see Setting#parseFrom(java.lang.Object) 
     * @since 0.1
     */
    @Override
    public String parseFrom(String u)
    {
        return u;
    }
    //</editor-fold>
}
