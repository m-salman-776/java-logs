package Project101.ChatSystem.classes;

import Project101.ChatSystem.interfaces.Observer;
import Project101.ChatSystem.interfaces.Subject;
import lombok.Getter;

@Getter
public class User implements Observer , Comparable<User> {
    int id;
    String name;
    private final Subject chatClient;

    public User(int id, String name, Subject chatClient){
        this.id= id;
        this.name = name;
        this.chatClient= chatClient;
        this.chatClient.addObserver(this);
    }
    @Override
    public void update(Message message) {
        System.out.println("Message : " + message.getContent() + " " + message.getSentAt());
    }


//    public void sendMessage(String s){
//        this.chatClient.sendAllMessage();
//    }

    @Override
    public int compareTo(User o) {
        return Integer.compare(this.id,o.id);
    }
}
