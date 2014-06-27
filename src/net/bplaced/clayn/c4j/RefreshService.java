package net.bplaced.clayn.c4j;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * This class saves several informations that are used to keep Fields from Objects 
 * up to date if the values stored with the reqzested key will be changed. 
 * It does only update when a new value was set and not when the Object was 
 * changed itself.
 *
 * @author Clayn
 * @since 0.1
 * @version 0.1
 */
public final class RefreshService
{

    //<editor-fold desc="Attribute">
    private final String key;
    private final Object requester;
    private final Field requestField;

    //</editor-fold>
    //<editor-fold desc="Private">
    //</editor-fold>
    //<editor-fold desc="Public">
    //</editor-fold>
    //</editor-fold>
    //<editor-fold desc="Konstruktoren">
    /**
     * Constructs a RefreshService with the given parameters. This should only
     * be done by classes extending {@link Refresher}
     *
     * @param key the requested key
     * @param requester the Object that should be updated
     * @param requestField the name of the Field to update
     * @since 0.1
     */
    RefreshService(String key, Object requester, Field requestField)
    {
        this.key = key;
        this.requester = requester;
        this.requestField = requestField;
    }

    /**
     * Returns the Field that should be updated.
     *
     * @return the field to update
     * @since 0.1
     */
    public Field getRequestField()
    {
        return requestField;
    }

    /**
     * Returns the Object that requests the updates.
     *
     * @return the objects to be updated
     * @since 0.1
     */
    public Object getRequester()
    {
        return requester;
    }

    /**
     * Returns the key for the Objects that will be used to update.
     *
     * @return the key for the requested Objects
     * @since 0.1
     */
    public String getKey()
    {
        return key;
    }

    /**
     * Returns {@code true} if the given Object is equals with this {@link RefreshService}
     *  {@code false} otherwise. Two {@link RefreshService} are equal if they
     * request the same key, the requesters are instances from the same
     * {@link Class} and the field to update has the same name.
     * <br><br><b>Documentation from
     * {@link Object#equals(java.lang.Object)}:</b><br> {@inheritDoc }
     *
     * @param obj the other Object to check
     * @return {@code true} if both ({@code this} and {@code obj}) are equal,
     * {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || !(obj instanceof RefreshService))
        {
            return false;
        }
        RefreshService oth = (RefreshService) obj;
        return (key.equals(oth.getKey())) && (requester.getClass().equals(oth.
                getRequester().getClass())) && (requestField.getName().
                equals(oth.getRequestField().getName()));
    }

    /**
     * {@inheritDoc }
     *
     * @return Returns a hashcode for this specific {@link RefreshService}
     */
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.key);
        hash = 71 * hash + Objects.hashCode(this.requester.getClass().getName());
        hash = 71 * hash + Objects.hashCode(this.requestField.getName());
        return hash;
    }

    @Override
    public String toString()
    {
        return new StringBuilder().append("[Key: ").append(key).
                append("; Instance: ").append(requester.toString()).
                append("; Field: ").append(requestField.
                        getName()).append(
                        "]").toString();
    }

}
