package net.bplaced.clayn.c4j.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * This class contains utility methods used by Config4J.
 *
 * @author Clayn
 * @since 0.1
 * @version 0.1
 */
public final class ConfigUtil
{
    //<editor-fold desc="Attribute">

    //</editor-fold>
    //<editor-fold desc="Konstruktoren">
    /**
     * Prevent Instances of this class
     */
    private ConfigUtil()
    {
    }
    //</editor-fold>
    //<editor-fold desc="Private">
    //</editor-fold>
    //<editor-fold desc="Public">
    /**
     * Sets the value for the given field to the new Object and returns if the
     * changing was successfull or not. This method may fail under certain
     * circumstances, such as the given field does not exist or some other
     * exceptions will occur. The given field may be final or private but will
     * changed anyway. Modifiers of this field will not changed and will stay
     * the same as before.
     *
     * @param fl the field that should be manipulated
     * @param inst the instance from the field that should be changed. May be
     * {@code null} if the field is static.
     * @param val the new value for the given field. Must be from a compatible
     * type
     * @return {@code true} if and only if the value of {@code fl} (which should
     * be changed) is equal to {@code val}, {@code false} otherwise. In other
     * words this method will return {@code fl.get(inst).equals(val);}.
     * @throws NoSuchFieldException if
     * {@link Class#getDeclaredField(java.lang.String)} throws an exception
     * which should not happen.
     * @throws IllegalAccessException if manipilating on the field fails for
     * example if an {@link SecurityManager} is installed.
     * @since 0.1
     */
    public static boolean setField(Field fl, Object inst, Object val) throws
            NoSuchFieldException, IllegalAccessException
    {
        fl.setAccessible(true);
        Field modField = Field.class.getDeclaredField("modifiers");
        int modold = fl.getModifiers();
        modField.setAccessible(true);
        modField.setInt(fl, fl.getModifiers() & ~Modifier.FINAL);
        fl.set(inst, val);
        modField.setInt(fl, modold);
        return fl.get(inst).equals(val);
    }
    //</editor-fold>
}
