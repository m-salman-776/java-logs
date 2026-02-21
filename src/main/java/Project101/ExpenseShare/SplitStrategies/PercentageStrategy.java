package Project101.ExpenseShare.SplitStrategies;

import Project101.ExpenseShare.Expense;
import Project101.ExpenseShare.ExpenseSplit;
import Project101.ExpenseShare.ExpenseType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PercentageStrategy implements SplitStrategy {
    @Override
    public List<ExpenseSplit> calculateSplits(Expense expense) {
        List<ExpenseSplit> splits = new ArrayList<>();
        Map<Integer, Double> splitMap = expense.getSplits();
        double totalPercentage = 0;

        for (Map.Entry<Integer, Double> entry : splitMap.entrySet()) {
            double amount = (entry.getValue() / 100.0) * expense.getTotalAmount();
            splits.add(new ExpenseSplit(expense.getId(), entry.getKey(), amount, ExpenseType.CONSUMER));
            totalPercentage += entry.getValue();
        }

        if (Math.abs(totalPercentage - 100.0) > 0.001) {
             throw new RuntimeException("Percentages did not add up to 100");
        }

        splits.add(new ExpenseSplit(expense.getId(), expense.getPayerId(), expense.getTotalAmount(), ExpenseType.PAYER));
        return splits;
    }
}