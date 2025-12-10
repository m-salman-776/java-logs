package Project101.Splitwise.V2;

import Project101.Splitwise.code.Split;

import java.util.List;

public interface SplitStrategy {
    // ONE job: Take the request and return the result.
    // Throws InvalidExpenseRequestException if inputs (like %) are wrong.
    List<Split> calculateSplits(ExpenseRequest request);
}
