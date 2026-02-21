package Project101.SplitWise.SplitStrategies;

import Project101.SplitWise.Expense;
import Project101.SplitWise.ExpenseSplit;
import Project101.SplitWise.ExpenseType;
import Project101.Splitwise.Split;
import Project101.Splitwise.SplitType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EqualStrategy implements SplitStrategy {
    @Override
    public List<ExpenseSplit> calculateSplits(Expense expense) {
        List<ExpenseSplit> list = new ArrayList<>();
        double amount = expense.getTotalAmount();
        Map<Integer,Double> splits = expense.getSplits();
        double totalAmount = 0.0;
        double splitAmount = amount / (double) splits.size();
        for (Integer userId : splits.keySet()){
            totalAmount += splitAmount;
            if (userId == expense.getPayerId()){
                continue;
            }
            list.add(new ExpenseSplit(expense.getId(),userId,splitAmount, ExpenseType.CONSUMER));
        }
        list.add(new ExpenseSplit(expense.getId(),expense.getPayerId(),splitAmount + (expense.getTotalAmount()-totalAmount), ExpenseType.CONSUMER));
        list.add(new ExpenseSplit(expense.getId(),expense.getPayerId(),expense.getTotalAmount(), ExpenseType.PAYER));
        return list;
    }
}
