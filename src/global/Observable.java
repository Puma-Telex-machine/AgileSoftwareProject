package global;

public interface Observable<Observer> {
    void subscribe(Observer observer);
}
