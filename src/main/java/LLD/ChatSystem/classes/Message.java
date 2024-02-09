package LLD.ChatSystem.classes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    int id;
    int senderId;
    String content;
    long sentAt;
    long seenAt;
    public Message(int id, int senderId, String content){
        this.id = id;
        this.senderId = senderId;
        this.seenAt = System.currentTimeMillis() / 1000;
    }
    public void updateSeenAt(long seenAt){
        this.seenAt = seenAt;
    }
}


