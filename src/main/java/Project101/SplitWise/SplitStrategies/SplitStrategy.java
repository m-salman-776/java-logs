package Project101.SplitWise.SplitStrategies;

import Project101.SplitWise.Expense;
import Project101.SplitWise.ExpenseSplit;

import java.util.List;

public interface SplitStrategy {
    List<ExpenseSplit> calculateSplits(Expense expense);
}
