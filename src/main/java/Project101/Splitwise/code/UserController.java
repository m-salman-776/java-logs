package Project101.Splitwise.code;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    List<User> userList;
    public UserController(){
        this.userList = new ArrayList<>();
    }

    public User getUser(long Id){
        for (User user : userList) {
            if (user.getUserId() == Id) return user;
        }
        return null;
    }
    public void  addUser(User user){
        this.userList.add(user);
    }
    public List<User> getUserList(){
        return this.userList;
    }
}
