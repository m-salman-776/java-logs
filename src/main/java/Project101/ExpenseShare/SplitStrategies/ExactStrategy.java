package Project101.ExpenseShare.SplitStrategies;

import Project101.ExpenseShare.Expense;
import Project101.ExpenseShare.ExpenseSplit;
import Project101.ExpenseShare.ExpenseType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExactStrategy implements SplitStrategy {
    @Override
    public List<ExpenseSplit> calculateSplits(Expense expense) {
        List<ExpenseSplit> splits = new ArrayList<>();
        Map<Integer, Double> splitMap = expense.getSplits();
        double totalSplitAmount = 0;

        for (Map.Entry<Integer, Double> entry : splitMap.entrySet()) {
            splits.add(new ExpenseSplit(expense.getId(), entry.getKey(), entry.getValue(), ExpenseType.CONSUMER));
            totalSplitAmount += entry.getValue();
        }

        if (Math.abs(totalSplitAmount - expense.getTotalAmount()) > 0.001) {
            throw new RuntimeException("Split amounts did not match total amount");
        }

        splits.add(new ExpenseSplit(expense.getId(), expense.getPayerId(), expense.getTotalAmount(), ExpenseType.PAYER));
        return splits;
    }
}