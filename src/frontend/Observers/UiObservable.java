package frontend.Observers;

public interface UiObservable {
    /**
     * Adds the observer in this observables notify list
     * @param observer
     */
    void subscribe (UiObserver observer);
}
