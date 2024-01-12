package LLD.MoviesBooking.Repository;

import LLD.MoviesBooking.Classes.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UserRepository {
    private Map<Long, User> userMap;

    public UserRepository(){
        userMap = new HashMap<>();
    }
    public User getUserById(Long userId){
        if (userMap.containsKey(userId)) return userMap.get(userId);
        return null; // todo exeption;
    }

    public boolean addUser(User user){
        userMap.put(user.getUseId(),user);
        return true;
    }


}

