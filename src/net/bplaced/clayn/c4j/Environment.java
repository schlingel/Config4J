package net.bplaced.clayn.c4j;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.bplaced.clayn.c4j.anno.Env;
import net.bplaced.clayn.c4j.project.LogSystem;
import net.bplaced.clayn.c4j.util.ConfigUtil;

/**
 * An {@link Environment} is a storage class which can store any Object other
 * than the same {@link Environment} ({@code this}) paired with a key. An
 * Environment can also be used to automatic fill fields in other classes (if
 * static) or Objects. All Objects stored in the Environment that implement
 * {@link Serializable} can also be stored persistent in a given datasink
 * ({@link #save(java.io.OutputStream)} for more information). Other than
 * {@link Configuration} this class is meant to store objects that aren´t
 * something like options. However an Environment can be used the same way as an
 * {@link Configuration} but not other way around.
 *
 * @author Clayn
 * @version 0.1
 * @since 0.1
 * @see Configuration
 * @see SplittedEnvironment
 */
public class Environment extends Refresher implements Configurator
{

    //<editor-fold desc="Attribute">
    /**
     * The map that represents the storage for Objects and key to save
     */
    protected final Map<String, Object> environment = new HashMap<>();

    //</editor-fold>
    //<editor-fold desc="Konstruktoren">
    /**
     * Generates a new, empty Environment with no values stored.
     *
     * @since 0.1
     * @see #Environment(java.io.InputStream)
     */
    public Environment()
    {
        
    }

    /**
     * Generates a new Environment and loads all the key, value pairs stored in
     * the given InputStream. It is recommended that the InputStream is at the
     * beginning due to the fact that the {@link #load(java.io.InputStream)}
     * method (which is called within the constructor) will try to read the
     * amount of stored Object which should be at the beginning of the stream.
     *
     * @param in the InputStream to read the data from
     * @throws IOException if an IOException occures during the loading.
     * @since 0.1
     */
    public Environment(InputStream in) throws IOException
    {
        this();
        load(in);
    }

    //</editor-fold>
    //<editor-fold desc="Private">
    /**
     * A Wrapper class to combine a key and an Object into a single
     * {@link Serializable} Object which can be stored afterwards.
     * @author Clayn
     * @since 0.1
     * @version 0.1
     */
    public static class StoredObject implements Serializable
    {

        /**
         * The key under which the {@code value} was stored.
         */
        private final String key;
        /**
         * The Object that was stored under the {@code key}.
         */
        private final Object value;

        /**
         * Returns the key under which the stored Object was stored in the
         * {@link Environment}
         *
         * @return the key for the stored Object
         */
        public String getKey()
        {
            return key;
        }

        /**
         * Returns the Object which was stored under a given key in the {@link
         * Environment}.
         *
         * @return the stored Object
         */
        public Object getValue()
        {
            return value;
        }

        private StoredObject(String key, Object value)
        {
            this.key = key;
            this.value = value;
        }

    }

    //</editor-fold>
    //<editor-fold desc="Public">
    /**
     * Stores all those values from this {@link Environment} into the given
     * OutpuStream. Only those values will be stored which implements
     * {@link Serializable}. At the beginning of the OutputStream this method
     * will write the amount of Objects that will be stored, so you must make
     * sure to read this number if you want to read the objects by
     * yourself.<br><br>
     * Note: To save the value and the key they will be wrappend into an
     * {@link StoredObject} so you have to cast to {@link StoredObject} and then
     * you can work with it. Also this operation is Threadsafe which means that
     * all changes made during the saving process will not be saved.
     *
     *
     * @param out the output where wo store the values and keys. If this
     * argument is {@code null} this method will simply return.
     * @throws IOException if an IOException occures during the writing
     * @see #load(java.io.InputStream)
     * @since 0.1
     */
    public final void save(OutputStream out) throws IOException
    {
        if (out == null)
        {
            return;
        }
        synchronized (environment)
        {
            List<StoredObject> stored = new ArrayList<>(environment.size());
            for (String key : environment.keySet())
            {
                Object object = environment.get(key);
                if (object instanceof Serializable)
                {
                    StoredObject obj = new StoredObject(key, object);
                    stored.add(obj);
                }
            }
            try (ObjectOutputStream objout = new ObjectOutputStream(out))
            {
                objout.writeInt(stored.size());
                for (StoredObject storedObject : stored)
                {
                    objout.writeObject(storedObject);
                }
                objout.flush();
            }
        }
    }

    /**
     * Loads all Objects from the given Inputstream into this
     * {@link Environment}. The InputStream must start with the number of
     * Objects to read to prevent an {@link EOFException}. Also all Objects must
     * be instances of {@link StoredObject} which should be the case if the
     * source of the inputstream was originally written by
     * {@link #save(java.io.OutputStream)}. If all those conditions are correct
     * all stored Objects will be stored under theyre specific key.
     * <br><br>
     * Note: This method is Threadsafe so theire will be no changes at the
     * current values during the loading process.
     *
     * @param in the Inputstream to load the Objects from. If this argument is
     * {@code null} this method will simply return.
     * @throws IOException if an IOException occures during the loading
     * @since 0.1
     */
    public final void load(InputStream in) throws IOException
    {
        if (in == null)
        {
            return;
        }
        synchronized (environment)
        {
            try
            {
                try (ObjectInputStream objin = new ObjectInputStream(in))
                {
                    int count = objin.readInt();
                    for (int i = 0; i < count; i++)
                    {
                        StoredObject stored = (StoredObject) objin.readObject();
                        environment.put(stored.getKey(), stored.getValue());
                    }
                }
            }
            catch (EOFException ex)
            {
                Logger.getLogger("Unexcpected Exception").warning(
                        "There was an EOFException during the loading of the Environment which should not occur");
            }
            catch (ClassNotFoundException ex)
            {
                LogSystem.getLogger(Environment.class).
                        log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Configures the given Object depending on the presence of the {@link Env}
     * annotation. Each field of the given Object that is marked with
     * {@link Env} will be filled with the value that was stored under the
     * requested key from the {@link Env} key. If {@link Env#refresh()} returns
     * {@code true} this field will be added to the fields that will be updated
     * when the value for the specific {@code key} was changed. This behaviour
     * can be also started manually with
     * {@link #add(java.lang.String, java.lang.Object, java.lang.String)}.
     *
     * @param <T> the type of the Object to configure
     * @param obj the object that should be configured
     * @return the exact Object that was put into this method with configured
     * fields.
     * @since 0.1
     * @see #add(java.lang.String, java.lang.Object, java.lang.String)
     * @see Env
     * @see Configurator#configure(java.lang.Object)
     */
    @Override
    public <T> T configure(T obj)
    {
        Class cl = obj.getClass();
        Field[] fields = cl.getDeclaredFields();
        for (Field f : fields)
        {
            if (f.isAnnotationPresent(Env.class))
            {
                try
                {
                    Env conf = f.getAnnotation(Env.class);
                    String key = conf.value();
                    ConfigUtil.setField(f, obj, get(key));
                    if (conf.refresh())
                    {
                        add(key, obj, f.getName());
                    }
                }
                catch (NoSuchFieldException | IllegalArgumentException |
                        IllegalAccessException ex)
                {
                    LogSystem.getLogger(Environment.class).
                            log(Level.WARNING, null, ex);
                }
            }
        }
        return obj;
    }

    /**
     * Puts the given value into the {@link Environment} stored under the given
     * key. All attached {@link Observer} will be notified with the {@code key}
     * as argument.
     *
     * @param key the key for the given value
     * @param val the value to be stored
     * @return the Object that was stored with this key before or {@code null}
     * if no Object was stored before.
     * @see #put(net.bplaced.clayn.c4j.Setting, java.lang.Object)
     * @throws IllegalArgumentException if you try to store the environment
     * itself. In other words when {@code val==this} returns {@code true}.
     * @since 0.1
     */
    public Object put(String key, Object val)
    {
        if (val == this)
        {
            throw new IllegalArgumentException(
                    "Can´t store the environment within itself");
        }
        inform(key, val);
        return environment.put(key, val);
    }

    /**
     * Puts the given value into the {@link Environment} stored under the key
     * returned by {@link Setting#getSettingsKey()} from the given Setting. All
     * attached {@link Observer} will be notified with the used key as argument.
     *
     * @param set the Setting to use the key from
     * @param val the value to be stored
     * @return the Object that was stored with the used key before or
     * {@code null} if no Object was stored before.
     * @see #put(java.lang.String, java.lang.Object)
     * @throws IllegalArgumentException if you try to store the environment
     * itself. In other words when {@code val==this} returns {@code true}.
     * @since 0.1
     */
    public Object put(Setting set, Object val)
    {
        return put(set.getSettingsKey(), val);
    }

    /**
     * Returns the value that was stored with the key getting from
     * {@link Setting#getSettingsKey()} and tries to cast it. If no such value
     * was found the {@code def} value will be returned
     *
     * @param <T> the type in which the returned object should be casted
     * @param set the Setting which contains the key to recieve
     * @param def the default value which will be returned if no Object was
     * stored with the given key.
     * @return the Object that was stored which the specific key or {@code def}
     * if it was {@code null}
     * @see #get(java.lang.String)
     * @see #get(java.lang.String, java.lang.Object)
     * @see #get(net.bplaced.clayn.c4j.Setting)
     * @since 0.1
     * @throws ClassCastException If the stored Object can´t be cast to
     * {@code T}
     */
    public <T> T get(Setting set, T def)
    {
        return (T) get(set.getSettingsKey(), def);
    }

    /**
     * Returns the value that was stored with the given key and tries to cast
     * it. If no such value was found the {@code def} value will be returned
     *
     * @param <T> the type in which the returned object should be casted
     * @param key the key to recieve the value from
     * @param def the default value which will be returned if no Object was
     * stored with the given key.
     * @return the Object that was stored which the specific key or {@code def}
     * if it was {@code null}
     * @see #get(java.lang.String)
     * @see #get(net.bplaced.clayn.c4j.Setting, java.lang.Object)
     * @see #get(net.bplaced.clayn.c4j.Setting)
     * @since 0.1
     * @throws ClassCastException If the stored Object can´t be cast to
     * {@code T}
     */
    public <T> T get(String key, T def)
    {
        Object back = environment.get(key);
        return (T) (back == null ? def : back);
    }

    /**
     * Returns the value that was stored with the key from
     * {@link Setting#getSettingsKey()} and tries to cast it. I no value was
     * found this method will return {@code null}. This method is equal to
     * {@code get(set,null);}
     *
     * @param <T> the type in which the returned object should be casted
     * @param set the Setting which contains the key to recieve
     * @return the value that was stored with the specific key or {@code null}
     * if no such value was found
     * @see #get(java.lang.String)
     * @see #get(net.bplaced.clayn.c4j.Setting, java.lang.Object)
     * @see #get(java.lang.String, java.lang.Object)
     * @since 0.1
     * @throws ClassCastException If the stored Object can´t be cast to
     * {@code T}
     */
    public <T> T get(Setting set)
    {
        return get(set, null);
    }

    /**
     * Returns the value that was stored with the given key and tries to cast
     * it. I no value was found this method will return {@code null}. This
     * method is equal to {@code get(key,null);}
     *
     * @param <T> the type in which the returned object should be casted
     * @param key the key to recieve the value from
     * @return the value that was stored with the given key or {@code null} if
     * no such value was found
     * @see #get(net.bplaced.clayn.c4j.Setting)
     * @see #get(net.bplaced.clayn.c4j.Setting, java.lang.Object)
     * @see #get(java.lang.String, java.lang.Object)
     * @since 0.1
     * @throws ClassCastException If the stored Object can´t be cast to
     * {@code T}
     */
    public <T> T get(String key)
    {
        return get(key, null);
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj == null || !(obj instanceof Environment) ? false : environment.
                equals(((Environment) obj).environment);
    }

    @Override
    public int hashCode()
    {
        return environment.hashCode();
    }

    @Override
    public String toString()
    {
        return environment.toString();
    }
    //</editor-fold>

}
