package Project101.Splitwise.Services;

import Project101.Splitwise.Expense;
import Project101.Splitwise.SplitStrategy.SplitStrategyFactory;
import Project101.Splitwise.Split;
import Project101.Splitwise.SplitType;

import java.util.List;

public class ExpenseService {
    UserService userService;
    public ExpenseService(UserService userService){
        this.userService = userService;
    }
    public void addExpense(Expense expense, SplitType splitType){
        List<Split> splitList = SplitStrategyFactory.getStrategy(splitType).calculateSplits(expense);
        for (Split split:splitList) {
            userService.addExpend(split,expense.getPayerId());
        }
    }
}
