package Project101.Splitwise;
import lombok.Getter;
import java.util.List;
import java.util.Map;
@Getter
public class Expense {
    int payerId;
    String description;
    List<Integer> participants;
    Map<String,Double> expenseSplit;
    double amount;
    public Expense(int payerId, String description,double amount, List<Integer> participants){
        this.participants = participants;
        this.amount = amount;
        this.payerId = payerId;
        this.description = description;
    }
}
