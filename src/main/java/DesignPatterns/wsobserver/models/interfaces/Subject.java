package DesignPatterns.wsobserver.models.interfaces;

public interface Subject {
    void addObserver(Observer o);
    void notifyObservers();
    void removeObserver(Observer o);
}


