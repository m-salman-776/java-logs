package Project101.MeetingScheduler.services;

import Project101.MeetingScheduler.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    Map<Long, User> userMap;
    long userId;
    public UserService(){
        userMap = new HashMap<>();
        this.userId = 0;
    }
    public User addUser(String name){
        this.userId += 1;
        User user = new User(this.userId,name);
        userMap.put(this.userId,user);
        return user;
    }
    public boolean addEventInvitation(long eventId,long userId){
        if (!userMap.containsKey(userId)) return false;
        userMap.get(userId).getEventSet().add(eventId);
        return true;
    }
}
