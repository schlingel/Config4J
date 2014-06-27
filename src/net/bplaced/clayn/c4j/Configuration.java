package net.bplaced.clayn.c4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Observer;
import java.util.Properties;
import java.util.logging.Level;
import net.bplaced.clayn.c4j.anno.Configure;
import net.bplaced.clayn.c4j.project.LogSystem;
import net.bplaced.clayn.c4j.util.ConfigUtil;

/**
 * A Configuration is a class for an easy way to store application informations
 * and settings that also be stored and loaded. Alter to {@link Environment}
 * this class is meant to store data that can be converted to and from String.
 *
 *
 * @author Clayn
 * @see Environment
 * @see SplittedConfiguration
 * @since 0.1
 * @version 0.1
 */
public class Configuration extends Refresher implements Configurator
{

    //<editor-fold desc="Attribute">
    /**
     * The {@link Properties} to store all informations (as Strings).
     */
    protected final Properties properties = new Properties();
    //</editor-fold>

    //<editor-fold desc="Konstruktoren">
    /**
     * Creates a new, empty Configuration without any stored values.
     *
     * @since 0.1
     * @see #Configuration(java.io.InputStream)
     */
    public Configuration()
    {

    }

    /**
     * Creates a new Configuration and loads all key,value pairs into the new
     * Configuration. The way how the values are loaded is the same as
     * {@link Properties#load(java.io.InputStream)} so you can see the java
     * documentation. As in the linked method the given Inputstream remains open
     * afterwards.
     *
     * @param in the InputStream to load the values from.
     * @throws IOException if an IOException occures during the loading.
     * @see Properties#load(java.io.InputStream)
     * @see #Configuration()
     * @since 0.1
     */
    public Configuration(InputStream in) throws IOException
    {
        this();
        load(in);
    }
    //</editor-fold>

    //<editor-fold desc="Private">
    //</editor-fold>
    //<editor-fold desc="Public">
    /**
     * Saves this Configuration into the given OutputStream and adds the given
     * comment to it. This method simply calls
     * {@link Properties#store(java.io.OutputStream, java.lang.String)}. So the
     * exact behaviour can be found there. The given OutputStream remains open
     * afterwards.
     *
     * @param com the comment added into the OutputStream
     * @param out the OutputStream to store the values.
     * @throws IOException if an IOException occures during the saving.
     * @see #load(java.io.InputStream)
     * @since 0.1
     */
    public final void save(String com, OutputStream out) throws IOException
    {
        properties.store(out, com);
    }

    /**
     * Loads all the values from the given InputStream and stores them into this
     * Configuration. This method calls
     * {@link Properties#load(java.io.InputStream)} so the exact behaviour can
     * be found there. The stream remains open afterwards.
     *
     * @param in the Inputstream to load the values from
     * @throws IOException
     * @since 0.1
     * @see #save(java.lang.String, java.io.OutputStream)
     */
    public final void load(InputStream in) throws IOException
    {
        properties.load(in);
    }

    /**
     * Returns the value stored with the key from the given {@link Setting} or
     * the {@code def} value if no value was found. The given {@link Setting}
     * will be used to parse the stored String back to the wished type.
     *
     * @param <T> The type of the Object that was stored before and into the
     * value will be parsed.
     * @param set the Setting that will be used to generate the key and parse
     * the stored String.
     * @param def the default value that will be returned if the found value was
     * {@code null}.
     * @return the value that was parsed from the String stored under the
     * generated key or {@code def} if the value was {@code null}.
     * @see #get(net.bplaced.clayn.c4j.Setting)
     * @since 0.1
     */
    public <T> T get(Setting<T> set, T def)
    {
        T t = set.parseFrom(properties.getProperty(set.getSettingsKey()));
        return t == null ? def : t;
    }

    /**
     * Returns the value stored with the key from the given {@link Setting} or
     * {@code null} if no value was found (or {@code null} was stored). The
     * given {@link Setting} will also be used to parse the stored value into
     * the needed type.
     *
     * @param <T> the type, the value should be parsed to. The parsing will be
     * done by the given {@link Setting}
     * @param set the {@link Setting} to get the key from and to parse the value
     * into the needed type.
     * @return the stored value parsed into {@code T} or {@code null} if it was
     * set so or no value was found.
     * @see #get(net.bplaced.clayn.c4j.Setting, java.lang.Object)
     * @since 0.1
     */
    public <T> T get(Setting<T> set)
    {
        return get(set, null);
    }

    /**
     * Stores the given value into this {@link Configuration} stored under the
     * key from the given {@link Setting}. Due to the fact that all values are
     * stored as {@link String} the given {@link Setting} will be used to parse
     * the value into a String. After inserting the parsed value all registered
     * {@link RefreshService} will be updated and all registered
     * {@link Observer} will be informed with the generated key (from
     * {@link Setting#getSettingsKey()}) as argument.
     *
     * @param <T> the type of the Object that will be stored and parsed.
     * @param set the Setting that will be used to parse and generate the needed
     * key.
     * @param val the value that will be parsed and stored.
     * @return the old value that was stored with the given key or {@code null}
     * if no old value was found.
     * @since 0.1
     */
    public <T> T put(Setting<T> set, T val)
    {
        T old = get(set);
        properties.setProperty(set.getSettingsKey(), set.parseTo(val));
        inform(set.getSettingsKey(), val);
        return old;
    }

    /**
     * Configures the given Object and returns it afterwards. All fields from
     * the given Object that are marked with the {@link Configure} annotation
     * will be set with the requested key using the {@link Setting} that was
     * specified with the annotation. Note that only those {@link Setting}'s can
     * be used that provide a consturctor with a single String as argument. If
     * {@link Configure#refresh()} returns {@code true} a new
     * {@link RefreshService} will be added for this field.
     *
     * @param <T> the type of the Object that will be configured.
     * @param obj the Object that will be configured
     * @return the same Object with updated fields.
     * @since 0.1
     */
    @Override
    public <T> T configure(T obj)
    {
        Class cl = obj.getClass();
        Field[] fields = cl.getDeclaredFields();
        for (Field f : fields)
        {
            if (f.isAnnotationPresent(Configure.class))
            {
                try
                {
                    Configure conf = f.getAnnotation(Configure.class);
                    String key = conf.key();
                    Class<? extends Setting> set = conf.setting();
                    Setting setter = set.getConstructor(key.getClass()).
                            newInstance(key);
                    ConfigUtil.setField(f, obj, get(setter));
                    if (conf.refresh())
                    {
                        add(key, obj, f.getName());
                    }
                }
                catch (NoSuchMethodException | SecurityException |
                        InstantiationException | IllegalAccessException |
                        IllegalArgumentException | InvocationTargetException |
                        NoSuchFieldException ex)
                {
                    LogSystem.getLogger(Configuration.class).
                            log(Level.WARNING, null, ex);
                }
            }
        }
        return obj;
    }
    @Override
    public boolean equals(Object obj)
    {
        return obj == null || !(obj instanceof Configuration) ? false : properties.
                equals(((Configuration) obj).properties);
    }

    @Override
    public int hashCode()
    {
        return properties.hashCode();
    }

    @Override
    public String toString()
    {
        return properties.toString();
    }
    //</editor-fold>
}
