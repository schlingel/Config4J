
package net.bplaced.clayn.c4j.set;
import java.awt.Color;
import net.bplaced.clayn.c4j.Setting;

/**
 * Implementation of {@link Setting} for {@link Color} values.
 * @author Clayn
 * @since 0.1
 * @version  0.1
 */
public class ColorSetting extends Setting<Color>
{

    /**
     * Constructs the {@link ColorSetting} for the given key.
     * @param key this setting is used for.
     * @see Setting#Setting(java.lang.String) 
     * @since 0.1
     */
    public ColorSetting(String key)
    {
        super(key);
    }

    /**
     * Parses the given String into a new Color Object. The new color will use 
     * the {@link Color#Color(int)} constructor which should be kept in mind.
     * @param u the String that should be parsed.
     * @throws NumberFormatException if the given String was not a {@code int}.
     * @return a new Color Object created with {@code new Color(Integer.parseInt(u));} 
     * or {@code null} if the given String was {@code null}.
     * @see Setting#parseFrom(java.lang.Object)
     * @since 0.1
     */
    @Override
    public Color parseFrom(String u)
    {
        if(u==null)
            return null;
        return new Color(Integer.parseInt(u));
    }
    
    //<editor-fold desc="Attribute">
    
    //</editor-fold>

    //<editor-fold desc="Konstruktoren">
        
    //</editor-fold>

    //<editor-fold desc="Private">
        
    //</editor-fold>

    //<editor-fold desc="Public">
        
    //</editor-fold>

    /**
     * Returns the RGB-value for the given color parsed into a String.
     * @param t the color that will be parsed.
     * @return the RGB-values for this colors as String or {@code null} if the 
     * given Color was {@code null}.
     * @see Setting#parseTo(java.lang.Object) 
     * @since 0.1
     */
    @Override
    public String parseTo(Color t)
    {
        return t==null?null:Integer.toString(t.getRGB());
    }
}
