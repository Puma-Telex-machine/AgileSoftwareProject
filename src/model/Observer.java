package model;

import model.facades.BoxFacade;

public interface Observer {
    void addBox(BoxFacade box);
}
