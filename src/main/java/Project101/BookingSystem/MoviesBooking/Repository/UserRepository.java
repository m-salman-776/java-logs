package Project101.BookingSystem.MoviesBooking.Repository;

import Project101.BookingSystem.MoviesBooking.Model.User;

import java.util.HashMap;
import java.util.Map;

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

