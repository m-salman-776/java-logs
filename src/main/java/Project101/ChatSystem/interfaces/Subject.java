package Project101.ChatSystem.interfaces;

import Project101.ChatSystem.classes.Message;

public interface Subject {
    void notifyObserver(Message message);
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void sendAllMessage(Observer observer);
}
