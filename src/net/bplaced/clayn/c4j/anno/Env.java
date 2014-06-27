package net.bplaced.clayn.c4j.anno;

import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import net.bplaced.clayn.c4j.Environment;

/**
 * Annotation that will mark Fields of your class for the {@link Environment} 
 * to be loaded with a specific object stored before. Additionally you can decide 
 * if the field should be always up to date with the current stored objects. 
 * @author Clayn
 * @since 0.1
 * @version 0.1
 */
@Target({FIELD})
@Retention( value = RUNTIME )
public @interface Env 
{
    /**
     * Returns the key which should be used to recieve the requested object from 
     * the {@link Environment}. 
     * @return the key used for the {@link Environment}
     * @since 0.1
     */
    String value();
    
    /**
     * Returns if the field should be refreshed every time the value for the key 
     * returned by {@link #value()} is changed. Per default {@code false} will 
     * be returned.
     * @return {@code true} if the field should always be up to date, {@code false} 
     * otherwise.
     * @since 0.1
     * @see Configure#refresh() 
     */
    boolean refresh() default false;
}
