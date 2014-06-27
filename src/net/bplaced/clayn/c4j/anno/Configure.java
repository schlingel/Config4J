package net.bplaced.clayn.c4j.anno;

import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import net.bplaced.clayn.c4j.Configuration;
import net.bplaced.clayn.c4j.Setting;


/**
 * Annotation that will mark Fields of your class for the {@link Configuration} 
 * to be loaded with a specific object stored before. Additionally you can decide 
 * if the field should be always up to date with the current stored objects. 
 * @author Clayn
 * @since 0.1
 * @version 0.1
 */
@Target({FIELD})
@Retention( value = RUNTIME )
public @interface Configure 
{
    /**
     * Returns the key which will be used to fill this field from the specific 
     * value stored in the {@link Configuration}
     * @return the key used to fill this field
     * @see Env#value()
     * @since 0.1
     */
    String key();
    
    /**
     * The {@link Setting} implementation which is used to fill this field. Per 
     * default the values are stored as {@link String} and to parse them to 
     * the needed type the rigth {@link Setting} is needed.
     * @return the Setting implementation that will be used to parse the value 
     * for the field
     * @since 0.1
     */
    Class<? extends Setting> setting() ;
    /**
     * Returns if the field should be refreshed every time the value for the key 
     * returned by {@link #key()} is changed. Per default {@code false} will 
     * be returned.
     * @return {@code true} if the field should always be up to date, {@code false} 
     * otherwise.
     * @since 0.1
     * @see Env#refresh() 
     */
    boolean refresh() default false;
}
