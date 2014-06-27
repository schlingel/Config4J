package net.bplaced.clayn.c4j.set;

import net.bplaced.clayn.c4j.Setting;

/**
 * Implementation of the {@link Setting} class for {@code int} values.
 *
 * @author Clayn
 * @since 0.1
 * @version 0.1
 */
public class IntSetting extends Setting<Integer>
{

    //<editor-fold desc="Attribute">
    //</editor-fold>
    //<editor-fold desc="Konstruktoren">
    /**
     * Constructs the {@link IntSetting} for the given key.
     * @param key this setting is used for.
     * @see Setting#Setting(java.lang.String) 
     * @since 0.1
     */
    public IntSetting(String key)
    {
        super(key);
    }
    //</editor-fold>

    //<editor-fold desc="Private">
    //</editor-fold>
    //<editor-fold desc="Public">
    /**
     * Parses the given String into a int if it represents a parseable value. 
     * @param u the value to be parsed
     * @return a int value parsed from the given String or {@code null} if 
     * the value was {@code null}.
     * @throws NumberFormatException if the value could not be parsed
     * @see Setting#parseFrom(java.lang.Object) 
     * @since 0.1
     */
    @Override
    public Integer parseFrom(String u)
    {
        if (u == null)
        {
            return null;
        }
        return Integer.parseInt(u);
    }
    //</editor-fold>
}
