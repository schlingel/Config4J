package net.bplaced.clayn.c4j.util;

import java.util.Objects;

/**
 * Class to pair two Objects of an different, or same, type in another Object.
 * Such as {@link java.awt.Point} pairs two {@code int} this class can pair any type.
 *
 * @author Clayn
 * @since 0.1
 * @version 0.1
 * @param <T> Type of the first stored Object
 * @param <U> Type of the second stored Object
 */
public class Pair<T, U>
{

    //<editor-fold desc="Attribute">
    private T first;
    private U second;

    //</editor-fold>
    //</editor-fold>
    //<editor-fold desc="Konstruktoren">
    /**
     * Creates a new empty Pair with the initial values {@code null}.
     *
     * @since 0.1
     * @see #Pair(Object, Object)
     */
    public Pair()
    {
        this(null, null);
    }

    /**
     * Creates a new Pair with the given values as initial values.
     *
     * @param first
     * @param second
     * @since 0.1
     * @see #Pair()
     */
    public Pair(T first, U second)
    {
        this.first = first;
        this.second = second;
    }

    //</editor-fold>
    //<editor-fold desc="Private">
    //</editor-fold>
    //<editor-fold desc="Public">
    /**
     * Returns the first stored Object.
     *
     * @return the first Object in this Pair
     * @since 0.1
     * @see #setFirst(Object)
     */
    public T getFirst()
    {
        return first;
    }

    /**
     * Returns the second stored Object.
     *
     * @return the second Object in this Pair
     * @since 0.1
     * @see #setSecond(Object)
     */
    public U getSecond()
    {
        return second;
    }

    /**
     * Sets the first Object in this Pair.
     *
     * @param first the new first Object for this Pair
     * @since 0.1
     * @see #getFirst()
     */
    public void setFirst(T first)
    {
        this.first = first;
    }

    /**
     * Sets the second Object in this Pair.
     *
     * @param second the new second Object for this Pair
     * @since 0.1
     * @see #getSecond()
     */
    public void setSecond(U second)
    {
        this.second = second;
    }

    /**
     * Pairs two Objects together in a new Pair Object.
     *
     * @param <T> Type for the first Object
     * @param <U> Type for the second Object
     * @param t the first Object for the pair
     * @param u the second Object for the pair
     * @return a new Pair with the given Objects
     * @since 0.1
     * @see #Pair(Object, Object)
     */
    public static <T, U> Pair<T, U> pair(T t, U u)
    {
        return new Pair<>(t, u);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || !(obj instanceof Pair))
        {
            return false;
        }
        Pair other = (Pair) obj;
        return first.equals(other.first) && second.equals(other.second);
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.first);
        hash = 47 * hash + Objects.hashCode(this.second);
        return hash;
    }

    @Override
    public String toString()
    {
        return new StringBuilder().append("[First: ").append(first).
                append("; Second: ").append(second).append("]").toString();
    }

}
