package model.facades;

import model.boxes.Visibility;

public interface AttributeFacade {
    /**
     * Returns the name of the attribute
     * @return
     */
    String getName();

    /**
     * Sets the name of the attribute
     * @param name
     */
    void setName(String name);

    /**
     * Returns the type of the attribute, return-type for methods
     * @return
     */
    String getType();

    /**
     * Sets the type of the attribute
     * @param type
     */
    void setType(String type);

    /**
     * Return the visibility for the attribute
     * @return
     */
    Visibility getVisibility();

    /**
     * Sets the visibility for the attribute
     * @param visibility
     */
    void setVisibility(Visibility visibility);
}
