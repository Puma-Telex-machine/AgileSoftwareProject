package model;

public class Attribute {

    String name;
    Changeable changeable;
    Visibility visibility;

    enum Visibility{
        PRIVATE,
        PACKAGE_PRIVATE,
        PROTECTED,
        PUBLIC
    }

    enum Type{ //Probably requires non-enum solution
        INT
    }

    enum Changeable{
        NON_STATIC,
        STATIC,
        FINAL,
        STATIC_FINAL
    }

    Attribute(String name, Changeable changeable, Visibility visibility){
        this.name = name;
        this.changeable = changeable;
        this.visibility = visibility;
    }

    void SetName(String name){
        this.name = name;
    }

    void SetVisibility(Visibility visibility){
        this.visibility = visibility;
    }

    void SetChangeable(Changeable changeable){
        this.changeable = changeable;
    }




}
