package net.bplaced.clayn.c4j.set;

import java.io.File;
import net.bplaced.clayn.c4j.Setting;

/**
 * Implementation of the {@link Setting} class for {@code File} values.
 *
 * @author Clayn
 * @since 0.1
 * @version 0.1
 */
public class FileSetting extends Setting<File>
{

    //<editor-fold desc="Attribute">
    //</editor-fold>
    //<editor-fold desc="Konstruktoren">
    /**
     * Constructs the {@link FileSetting} for the given key.
     * @param key this setting is used for.
     * @see Setting#Setting(java.lang.String) 
     * @since 0.1
     */
    public FileSetting(String key)
    {
        super(key);
    }
    //</editor-fold>

    //<editor-fold desc="Private">
    //</editor-fold>
    //<editor-fold desc="Public">
    /**
     * Parses the given String into a new File Object. 
     * @param u the String that should be parsed.
     * @return a new File with the given String used as path or {@code null} 
     * if the given String was {@code null}.
     * @see Setting#parseFrom(java.lang.Object) 
     * @since 0.1
     */
    @Override
    public File parseFrom(String u)
    {
        if(u==null)
            return null;
        return new File(u);
    }
    //</editor-fold>
}
