package Project101.Splitwise.V2;

import Project101.Splitwise.code.Expense;
import Project101.Splitwise.code.Split;
import Project101.Splitwise.code.SplitType;
import Project101.Splitwise.code.User;

import java.util.List;
import java.util.stream.Collectors;

public class ExpenseSettlementService {
    DebtSimplificationService simplificationService;
//    SplitStrategyFactory strategyFactory;
    public ExpenseSettlementService(){
        simplificationService = new DebtSimplificationService();
    }
    public void addExpense(String payerId, double amount, List<String> userId, SplitType expenseType){
        SplitStrategy strategy =SplitStrategyFactory.getStrategy(expenseType);
        List<User> userList = userId.stream().map(user -> new User(Long.valueOf(user),"user_name"+user)).collect(Collectors.toList());
        ExpenseRequest expenseRequest = new ExpenseRequest(new User(Long.valueOf(payerId),"user_name"+payerId),amount,userList);
        List<Split> splitList = strategy.calculateSplits(expenseRequest);
    }
    public void addExpense(String payerId, double amount, List<String> userId, List<SplitDetails> splitList, SplitType expenseType){

    }
}
