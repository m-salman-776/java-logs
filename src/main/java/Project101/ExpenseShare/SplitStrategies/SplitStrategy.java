package Project101.ExpenseShare.SplitStrategies;

import Project101.ExpenseShare.Expense;
import Project101.ExpenseShare.ExpenseSplit;

import java.util.List;

public interface SplitStrategy {
    List<ExpenseSplit> calculateSplits(Expense expense);
}
