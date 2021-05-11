package global;

import java.util.ArrayList;

public class Observers {
    private final ArrayList<Observer> observers = new ArrayList<>();

    public void add(Observer observer) {
        observers.add(observer);
    }

    public void update() {
        for (Observer o : observers) {
            o.update();
        }
    }
}
