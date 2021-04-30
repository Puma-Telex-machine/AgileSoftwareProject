package model;

import model.boxes.Box;
import model.facades.BoxFacade;

public interface Observer {
    public void addBox(BoxFacade box);
}
