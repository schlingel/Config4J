package net.bplaced.clayn.c4j.util;

/**
 * Objects that implement this Interface will be able to be parsed from one Type 
 * to another and back. For example all Numbers can be parsed to strings and 
 * other way round.
 * @author Clayn
 * @param <T> One type that can be parsed
 * @param <U> Second Type that can be parsed
 * @since 0.1
 * @version 0.1
 */
public interface Parseable<T,U>
{
    /**
     * Parses a given Instance of the first Type into the second one. 
     * @param t the Instance that should be parsed
     * @return a Instance of the second type that was parsed
     * @since 0.1
     * @see #parseFrom(java.lang.Object)
     */
    public U parseTo(T t);
    
    /**
     * Parses a given Instance of the second Type into the first one. 
     * @param u the Instance that should be parsed
     * @return a Instance of the first type that was parsed
     * @since 0.1
     * @see #parseTo(java.lang.Object)
     */
    public T parseFrom(U u);
}
