package Project101.Splitwise.Services;

import Project101.Splitwise.User;
import Project101.Splitwise.Split;
import lombok.Getter;
import java.util.HashMap;
import java.util.Map;
@Getter
public class UserService {
    Map<Integer, User> userMap;
    int userId;
    public UserService(){
        userMap = new HashMap<>();
        this.userId = 0;
    }
    public void addExpend(Split split, int payerId){
        User borrower = userMap.get(split.getUserId());
        User payer = userMap.get(payerId);
        borrower.addTransaction(payerId,-split.getAmount());
        payer.addTransaction(split.getUserId(),split.getAmount());
//        borrower.summariseBalance();
//        borrower.summariseBalance();
    }
    public void addUser(String name){
        this.userId += 1;
        this.userMap.put(userId, new User(userId,name));
    }
}
