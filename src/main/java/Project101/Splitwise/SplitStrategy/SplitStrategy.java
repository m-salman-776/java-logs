package Project101.Splitwise.SplitStrategy;

import Project101.Splitwise.Expense;
import Project101.Splitwise.Split;

import java.util.List;

public interface SplitStrategy {
    // ONE job: Take the request and return the result.
    // Throws InvalidExpenseRequestException if inputs (like %) are wrong.
    List<Split> calculateSplits(Expense request);
}
