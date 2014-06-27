
package net.bplaced.clayn.c4j.set;

import net.bplaced.clayn.c4j.Setting;

/**
 * Implementation of the {@link Setting} class for {@code long} values.
 *
 * @author Clayn
 * @since 0.1
 * @version 0.1
 */
public class LongSetting extends Setting<Long>
{

    /**
     * Constructs the {@link LongSetting} for the given key.
     * @param key this setting is used for.
     * @see Setting#Setting(java.lang.String) 
     * @since 0.1
     */
    public LongSetting(String key)
    {
        super(key);
    }

    /**
     * Parses the given String into a long if it represents a parseable value. 
     * @param u the value to be parsed
     * @return a long value parsed from the given String or {@code null} if 
     * the value was {@code null}.
     * @throws NumberFormatException if the value could not be parsed
     * @see Setting#parseFrom(java.lang.Object) 
     * @since 0.1
     */
    @Override
    public Long parseFrom(String u)
    {
        return u==null?null:Long.parseLong(u);
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
