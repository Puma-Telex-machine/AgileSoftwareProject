package model.boxes;

import global.Observable;
import model.boxes.*;
import global.point.ScaledPoint;
import model.facades.AttributeFacade;
import model.facades.MethodFacade;

import java.util.List;
import java.util.Set;

public interface BoxFacade extends Observable<BoxObserver> {

    /**
     * Set the name of the box.
     * @param name the name of the box.
     */
    void setName(String name);

    /**
     * Get the name of the box.
     * @return the name of the box.
     */
    String getName();


    /**
     * Get the box type (Class, Interface, Enum).
     * @return the name of the box.
     */
    BoxType getType();


    /**
     * Delete this box from the diagram.
     */
    void deleteBox();


    MethodFacade addMethod();
    /**
     * If a method with this name exists then delete the method.
     * If the method doesn't exist then do nothing.
     * @param method The method that should be deleted
     */
    void deleteMethod(MethodFacade method);

    /**
     * Returns all methods of this class.
     */
    List<MethodFacade> getMethods();


    /**
     * Creates and returns a new attribute.
     */
    AttributeFacade addAttribute();

    /**
     * If this attribute exists then delete it.
     * If the variable doesn't exist then do nothing.
     * @param attribute The attribute that should be deleted.
     */
    void deleteAttribute(AttributeFacade attribute);

    /**
     * Returns the all attributes of this class
     */
    List<AttributeFacade> getAttributes();


    void setVisibility(Visibility visibility);

    Visibility getVisibility();


    void addModifier(Modifier modifier);

    void removeModifier(Modifier modifier);

    Set<Modifier> getModifiers();


    /**
     * Set the position of the box.
     * @param point the position to move the box to.
     */
    void setPosition(ScaledPoint point);

    /**
     * Get the position of the box.
     * @return the position as a ScaledPoint
     */
    ScaledPoint getPosition();

    ScaledPoint getWidthAndHeight();
}
