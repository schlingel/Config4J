package net.bplaced.clayn.c4j.set;

import net.bplaced.clayn.c4j.Setting;

/**
 * Implementation of the {@link Setting} class for {@code boolean} values.
 *
 * @author Clayn
 * @since 0.1
 * @version 0.1
 */
public class BooleanSetting extends Setting<Boolean>
{

    /**
     * Constructs the {@link BooleanSetting} for the given key.
     * @param key this setting is used for.
     * @see Setting#Setting(java.lang.String) 
     * @since 0.1
     */
    public BooleanSetting(String key)
    {
        super(key);
    }

    /**
     * Parses the given String into an {@link Boolean}. 
     * @param u the String that should be parsed
     * @return {@code false} if the given String was {@code null}, otherwise 
     * {@link Boolean#parseBoolean(java.lang.String)}.
     * @see Setting#parseFrom(java.lang.Object) 
     * @since 0.1
     */
    @Override
    public Boolean parseFrom(String u)
    {
        if (u == null)
        {
            return false;
        }
        return Boolean.parseBoolean(u);
    }
    //<editor-fold desc="Attribute">

    //</editor-fold>
    //<editor-fold desc="Konstruktoren">
    //</editor-fold>
    //<editor-fold desc="Private">
    //</editor-fold>
    //<editor-fold desc="Public">
    //</editor-fold>
}
