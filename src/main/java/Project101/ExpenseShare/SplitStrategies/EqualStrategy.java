package Project101.ExpenseShare.SplitStrategies;

import Project101.ExpenseShare.Expense;
import Project101.ExpenseShare.ExpenseSplit;
import Project101.ExpenseShare.ExpenseType;

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
