package global;

import java.util.ArrayList;

public class Observers {
    private final ArrayList<Observer> observers = new ArrayList<>();
    boolean doesUpdate = true;

    public void stopUpdates() {
        doesUpdate = false;
    }

    public void startUpdates() {
        doesUpdate = true;
    }

    public void add(Observer observer) {
        if (observer == null) throw new NullPointerException("Tried adding null observer");
        observers.add(observer);
    }

    public void update() {
        if (doesUpdate) {
            for (Observer o : observers) {
                o.update();
            }
        } else System.out.println("Update was blocked manually");
    }
}
