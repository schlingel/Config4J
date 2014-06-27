package net.bplaced.clayn.c4j;

/**
 * Implementations of this interface will allow you to configure an Object in 
 * different ways. The exact way how the object will be configured depends on 
 * the implementation and should be well documented.
 * @author Clayn
 * @since 0.1
 * @version 0.1
 */
public interface Configurator 
{
    /**
     * Configures the given object and returns the same object. the reason to 
     * return the exact same object is that you can create, configure and save 
     * your object in one line such as: {@code MyObject obj=configure(new MyObject());}. 
     * 
     * @param <T> the type of the given object
     * @param obj the object that will be configured
     * @return the exact same object which was configured
     * @since 0.1
     */
    public <T> T configure(T obj);
}
