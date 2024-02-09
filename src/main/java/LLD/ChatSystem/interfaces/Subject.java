package LLD.ChatSystem.interfaces;

import LLD.ChatSystem.classes.Message;

public interface Subject {
    void notifyObserver(Message message);
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void sendAllMessage(Observer observer);
}
