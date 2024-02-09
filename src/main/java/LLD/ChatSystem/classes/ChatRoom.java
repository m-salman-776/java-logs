package LLD.ChatSystem.classes;

import LLD.ChatSystem.interfaces.Observer;
import LLD.ChatSystem.interfaces.Subject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatRoom implements Subject {
    int id;
    String name;
    Set<Observer> participants;
    List<Message> messageList;
    public ChatRoom(int id, String name){
        this.id = id;
        this.name = name ;
        participants = new HashSet<>();
        messageList = new ArrayList<>();
    }
    @Override
    public void notifyObserver(Message message) {
        for (Observer observer:participants){
            observer.update(message);
        }
    }

    @Override
    public void addObserver(Observer observer) {
        this.participants.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void sendAllMessage(Observer observer) {
        for (Message message : this.messageList){
            observer.update(message);
        }
    }
    public void sendMessage(int senderId, String content){
        Message message = new Message(this.messageList.size() + 1,senderId,content);
        this.messageList.add(message);
        this.notifyObserver(message);
    }
}
