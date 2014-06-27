
package net.bplaced.clayn.c4j.set;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import net.bplaced.clayn.c4j.Setting;
import net.bplaced.clayn.c4j.project.LogSystem;

/**
 * Implementation of the {@link Setting} class for {@code URL} values.
 *
 * @author Clayn
 * @since 0.1
 * @version 0.1
 */
public class URLSetting extends Setting<URL>
{

    /**
     * Constructs the {@link URLSetting} for the given key.
     * @param key this setting is used for.
     * @see Setting#Setting(java.lang.String) 
     * @since 0.1
     */
    public URLSetting(String key)
    {
        super(key);
    }

    /**
     * Parsed the given String into a URL if it was a valid value. This method 
     * will log any {@link MalformedURLException} with the {@link LogSystem}.
     * @param u the String that should be parsed.
     * @return a URL value parsed from the given String or {@code null} if the 
     * value was {@code null} or caused a {@link MalformedURLException}.
     * @see Setting#parseFrom(java.lang.Object) 
     * @since 0.1
     */
    @Override
    public URL parseFrom(String u)
    {
        if(u==null)
            return null;
        try
        {
            return new URL(u);
        }
        catch (MalformedURLException ex)
        {
            LogSystem.getLogger(URLSetting.class).log(Level.SEVERE, null,
                    ex);
            return null;
        }
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
