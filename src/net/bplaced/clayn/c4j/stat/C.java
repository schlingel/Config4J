
package net.bplaced.clayn.c4j.stat;

import net.bplaced.clayn.c4j.Configuration;

/**
 * Short static class to give fast and easy access to one stored and rechangable 
 * {@link Configuration}. This class has no special use other from save you time 
 * and programming on accessing your {@link Configuration}.
 * @see E
 * @author Clayn
 * @since 0.1
 * @version 0.1
 */
public final class C 
{
    //<editor-fold desc="Attribute">
    private static Configuration config;    
        
    //</editor-fold>

    //</editor-fold>
    //<editor-fold desc="Konstruktoren">
    //</editor-fold>
    //<editor-fold desc="Private">
    /**
     * Prevent instances of this class
     */
    private C(){}
    //</editor-fold>
    //<editor-fold desc="Public">
    /**
     * Sets the default {@link Configuration} for this class.
     * @param config the new {@link Configuration}
     */
    public static void setConfig(Configuration config)
    {
        C.config = config;
    }

    /**
     * Returns the stored {@link Configuration}
     * @return the stored {@link Configuration}
     */
    public static Configuration getConfig()
    {
        return config;
    }
    
    /**
     * Easily configures your object such as {@link Configuration} would do. In 
     * fact this method simple gives the object to the stored {@link Configuration} 
     * if its not {@code null}.
     * @param <T> the type of Object to be configured
     * @param obj the object that should be configured.
     * @return the same object as given to this method
     * @see Configuration#configure(java.lang.Object) 
     * @since 0.1
     */
    public static <T> T config(T obj)
    {
        if(C.config!=null)
            return C.config.configure(obj);
        return obj;
    }
}
