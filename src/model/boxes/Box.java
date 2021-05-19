package model.boxes;

import global.Observer;
import global.Observers;
import global.TextWidthCalculator;
import global.point.Scale;
import global.point.ScaledPoint;
import model.diagram.DiagramMediator;
import model.facades.AttributeFacade;
import model.facades.MethodFacade;
import model.facades.UndoChain;

import java.util.*;

/**
 * A generalized class representing a UML object, specifically classes/interfaces/enums.
 * Originally created by Emil Holmsten,
 * Expanded by Filip Hanberg.
 */
public class Box implements BoxFacade, Observer {
    //different fontsize on name and other
    private static final int START_HEIGHT = 3;
    private static final int START_WIDTH = 4;
    private static final double ROWS_PER_HEIGHT_UNIT = 0.4999;

    private String name;
    private final BoxType type;
    private final List<MethodFacade> methods = new ArrayList<>();
    private final List<AttributeFacade> attributes = new ArrayList<>();
    private final Set<Modifier> modifiers = new HashSet<>();
    private Visibility visibility = Visibility.PUBLIC;
    private ScaledPoint position;
    private final DiagramMediator diagram;
    private final UndoChain undoChain;


    public Box(DiagramMediator diagram, ScaledPoint position, BoxType type) {
        this.name = switch (type) {
            case CLASS -> "Class";
            case ABSTRACT_CLASS -> "Abstract Class";
            case INTERFACE -> "Interface";
            case ENUM -> "Enum";
            default -> "Box";
        };
        this.position = position;
        this.type = type;
        this.diagram = diagram;
        this.undoChain = diagram;
    }

    //region OBSERVABLE
    private final Observers observers = new Observers();

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void update() {
        diagram.updateBox(this);
        observers.update();
    }

    private Boolean undoActive = true;

    @Override
    public void updateUndo() {
        if(undoActive)
            undoChain.updateUndo();
    }

    @Override
    public void stopUndo(){
        undoActive = false;
    }

    @Override
    public void resumeUndo() {
        undoActive = true;
    }

    //endregion

    @Override
    public void setName(String name) {
        this.name = name;
        update();
        updateUndo();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BoxType getType() {
        return type;
    }

    @Override
    public void deleteBox() {
        diagram.removeBox(this);
        name = "THIS SHOULD NOT BE VISIBLE: BOX IS DELETED";
    }

    @Override
    public MethodFacade addMethod() {
        Method method = new Method(this);
        methods.add(method);
        method.subscribe(this);
        updateUndo();
        return method;
    }

    @Override
    public void deleteMethod(MethodFacade method) {
        methods.remove(method);
        update();
        updateUndo();
    }

    @Override
    public List<MethodFacade> getMethods() {
        return new ArrayList<>(methods);
    }

    @Override
    public AttributeFacade addAttribute() {
        Attribute attribute = new Attribute(this);
        attributes.add(attribute);
        attribute.subscribe(this);
        updateUndo();
        return attribute;
    }

    @Override
    public void deleteAttribute(AttributeFacade attribute) {
        attributes.remove(attribute);
        update();
        updateUndo();
    }

    @Override
    public List<AttributeFacade> getAttributes() {
        return new ArrayList<>(attributes);
    }

    @Override
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
        update(); //Behövs denna?
        updateUndo();
    }

    @Override
    public Visibility getVisibility() {
        return visibility;
    }

    @Override
    public void addModifier(Modifier modifier) {
        modifiers.add(modifier);
        update(); //Behövs denna?
        updateUndo();
    }

    @Override
    public void removeModifier(Modifier modifier) {
        // TODO: deleteModifier? (consistency)
        modifiers.remove(modifier);
        update();
        updateUndo();
    }

    @Override
    public Set<Modifier> getModifiers() {
        return modifiers;
    }

    public void setPosition(ScaledPoint point) {
        position = point;
    }

    @Override
    public void setAndUpdatePosition(ScaledPoint point) {
        //updateUndo();
        position = point;
        update();
    }

    @Override
    public ScaledPoint getPosition() {
        return position;
    }

    @Override
    //todo returns wrong height
    public ScaledPoint getWidthAndHeight() {
        return new ScaledPoint(Scale.Backend, getWidth(), getHeight());
    }

    private int getHeight() {

        if((getMethods().size() + getAttributes().size()==0)) return START_HEIGHT;
        //+1 to round up.
        int height = (int) ((getMethods().size() + getAttributes().size()) * ROWS_PER_HEIGHT_UNIT) + START_HEIGHT +1;

        return height;
    }

    private int getWidth() {

        ArrayList<String> names = new ArrayList<>();

        for (MethodFacade method : methods) {
            names.add(method.getString());
        }
        for (AttributeFacade attribute : attributes) {
            names.add(attribute.getString());
        }

        double longest=0;
        if(!names.isEmpty()){
            for (String n : names) {
                if(TextWidthCalculator.getInstance().computeTextWidthOther(n)>longest){
                    longest=TextWidthCalculator.getInstance().computeTextWidthOther(n);
                }
            }
        }
        if(longest<TextWidthCalculator.getInstance().computeTextWidthName(name)) longest = TextWidthCalculator.getInstance().computeTextWidthName(name);

        int i = new ScaledPoint(Scale.Frontend,longest,0).getX(Scale.Backend)+1;

        return Math.max(i,START_WIDTH);
    }
}
