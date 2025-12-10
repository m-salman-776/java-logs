package Project101.Splitwise.V2;

import Project101.Splitwise.code.SplitType;
import Project101.Splitwise.code.User;
import lombok.Getter;

import java.util.List;
import java.util.Map;
@Getter
public class ExpenseRequest {
    User payedBy;
    String description;
    List<User> userList;
    Map<String,Double> expenseSplit;
    double amount;
    public ExpenseRequest(User payedBy, double amount, List<User> userList){
        this.payedBy= payedBy;
        this.amount = amount;
        this.userList = userList;
    }
}
