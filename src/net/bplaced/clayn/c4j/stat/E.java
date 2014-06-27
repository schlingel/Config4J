package net.bplaced.clayn.c4j.stat;

import java.io.IOException;
import java.io.InputStream;
import net.bplaced.clayn.c4j.Environment;

/**
 * Short static class to give fast and easy access to one stored and rechangable
 * {@link Environment}. This class has no special use other from saving you time
 * and programming on accessing your {@link Environment}.
 *
 * @see C
 * @author Clayn
 * @since 0.1
 * @version 0.1
 */
public final class E
{

    //<editor-fold desc="Attribute">

    private static Environment environment;

    //</editor-fold>
    //</editor-fold>
    //<editor-fold desc="Konstruktoren">
    //</editor-fold>
    //<editor-fold desc="Private">
    /**
     * Prevent Instances of this class
     */
    private E(){}
    //</editor-fold>
    //<editor-fold desc="Public">
    /**
     * Creates and loads an new {@link Environment} from the given {@link InputStream} 
     * and sets it. After calling this method {@link #getEnvironment()} will return 
     * the new {@link Environment} which is also returned by this method. This 
     * method simply calls {@link Environment#Environment(java.io.InputStream)} 
     * to create the {@link Environment}.
     * @param in the Inputstream to load the data from
     * @return a newly created {@link Environment} with the data from the given 
     * stream.
     * @throws IOException if an IOException occures during the loading
     * @see Environment#Environment(java.io.InputStream)
     * @since 0.1
     */
    public static Environment init(InputStream in) throws IOException
    {
        environment=new Environment(in);
        return environment;
    }
    /**
     * Sets the default {@link Environment} for this class.
     *
     * @param environment the new {@link Environment}
     */
    public static void setEnvironment(Environment environment)
    {
        E.environment = environment;
    }

    /**
     * Returns the stored {@link Environment}
     *
     * @return the stored {@link Environment}
     */
    public static Environment getEnvironment()
    {
        return environment;
    }

    /**
     * Easily configures your object such as {@link Environment} would do. In
     * fact this method simple gives the object to the stored
     * {@link Environment} if its not {@code null}.
     *
     * @param <T> the type of Object to be configured
     * @param obj the object that should be configured.
     * @return the same object as given to this method
     * @see Environment#configure(java.lang.Object)
     * @since 0.1
     */
    public static <T> T config(T obj)
    {
        if (E.environment != null)
        {
            return environment.configure(obj);
        }
        return null;
    }
}
