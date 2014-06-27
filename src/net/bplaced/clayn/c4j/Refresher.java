package net.bplaced.clayn.c4j;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.logging.Level;
import net.bplaced.clayn.c4j.project.LogSystem;
import net.bplaced.clayn.c4j.util.ConfigUtil;

/**
 * Classes that extend this class are able to store {@link RefreshService}.
 * Those stored classes are used if some value was changed or set and the
 * specific field should be refreshed with the new value.
 *
 * @author Clayn
 * @since 0.1
 * @version 0.1
 */
public abstract class Refresher extends Observable
{

    //<editor-fold desc="Attribute">
    /**
     * Set of the {@link RefreshService} to be informed.
     */
    protected final Set<RefreshService> services = new HashSet<>();
    //</editor-fold>

    //<editor-fold desc="Konstruktoren">
    /**
     * Package Private constructor to prevent creations of a useless Refresher
     *
     * @since 0.1
     */
    Refresher()
    {

    }
    //</editor-fold>
    //<editor-fold desc="Private">
    /**
     * Updates all {@link RefreshService}´s that are interested in the given
     * {@code key} and the given {@code val}. Also this methos informs all
     * attached {@link Observer} with the {@code key} as argument.
     *
     * @param key the key which stores the changed Object
     * @param val the new Object for the given {@code key}
     * @since 0.1
     */
    protected final void inform(String key, Object val)
    {
        setChanged();
        notifyObservers(key);
        for (RefreshService ref : services)
        {
            if (ref.getKey().equals(key))
            {
                try
                {
                    ConfigUtil.setField(ref.getRequestField(), ref.
                            getRequester(),
                            val);
                }
                catch (NoSuchFieldException | IllegalArgumentException |
                        IllegalAccessException ex)
                {
                    LogSystem.getLogger(Refresher.class).
                            log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //</editor-fold>
    //<editor-fold desc="Public">
    /**
     * Creates a new {@link RefreshService} and registers it with the given
     * values. The {@link RefreshService} is used to update a specific field in
     * an Object with the value stored with the given {@code key}.
     *
     * @param key the key for the value that will be set to the field
     * @param request the Object in which the changes will be made
     * @param fieldName the name of the field to change
     * @return {@code true} if the {@link RefreshService} was sucessfully added
     * {@code false} otherwise.
     * @since 0.1
     */
    public boolean add(String key, Object request, String fieldName)
    {
        if (key == null || request == null || fieldName == null)
        {
            throw new IllegalArgumentException(
                    "No parameters of Refresher.add() is allowed to be null");
        }
        RefreshService serv = null;
        Field[] fields = request.getClass().getDeclaredFields();
        for (Field f : fields)
        {
            if (f.getName().equals(fieldName))
            {
                //Not sure if i should keep the restricition to only allow 
                //fields with my Annotations.
                //if (f.isAnnotationPresent(Configure.class) || f.
                //        isAnnotationPresent(Env.class))
                //{
                serv = new RefreshService(key, request, f);
                break;
                //}
            }
        }
        return serv == null ? false : services.add(serv);
    }

    //</editor-fold>
    
    @Override
    public boolean equals(Object obj)
    {
        //@TODO override equals in Refresher
        return super.equals(obj);
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.services);
        return hash;
    }
}
