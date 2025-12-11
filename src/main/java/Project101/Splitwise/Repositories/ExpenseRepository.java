package Project101.Splitwise.Repositories;
import Project101.Splitwise.Expense;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseRepository {
    Map<String, List<Expense>> groupExpense;
    public ExpenseRepository(){
        groupExpense = new HashMap<>();
    }
    List<Expense> getExpense(String groupId){
        return this.groupExpense.get(groupId);
    }
}
