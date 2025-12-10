package Project101.Splitwise.V2;

import Project101.Calendar.model.User;
import Project101.Splitwise.code.Split;

import java.util.List;

public class ExpenseService {
    SplitStrategy splitStrategy;
    public ExpenseService(SplitStrategy splitStrategy){
        this.splitStrategy = splitStrategy;
    }
    public void addExpense(ExpenseRequest expenseRequest){
        SplitStrategy splitStrategy1 = new EqualSplit();
        List<Split> splitList = splitStrategy1.calculateSplits(expenseRequest);
    }
}
