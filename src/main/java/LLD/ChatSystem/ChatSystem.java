package LLD.ChatSystem;

import LLD.ChatSystem.classes.ChatRoom;
import LLD.ChatSystem.classes.User;

import java.util.HashMap;
import java.util.Map;

public class ChatSystem {
    Map<Integer, User> userMap;
    Map<String, ChatRoom> chatRoomMap;

    public ChatSystem(){
        userMap = new HashMap<>();
        chatRoomMap = new HashMap<>();
    }

    public ChatRoom createChatRoom(String name){
        if (!chatRoomMap.containsKey(name)){
            chatRoomMap.put(name, new ChatRoom(124,name));
        }
        return this.chatRoomMap.get(name);
    }
    public void addMember(User user,ChatRoom chatRoom){
//        this.chatRoomMap.put()
    }
}
