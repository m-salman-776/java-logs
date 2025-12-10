package Assingment;

import java.util.ArrayList;
import java.util.List;

public class EqualStrategy implements SplitStrategy{
    @Override
    public List<Split> split(double amount,User paidBy, List<User> participant) {
        int totalUser = participant.size();
        double  remaning = amount;
        List<Split> splitList = new ArrayList<>();
        for (User user :participant){
            double payableAmount = amount / totalUser;
            remaning -= payableAmount;
            splitList.add(new Split(user,payableAmount));
        }
        if (remaning > 0) {
            // TODO Decided
        }
        return splitList;
    }
}
