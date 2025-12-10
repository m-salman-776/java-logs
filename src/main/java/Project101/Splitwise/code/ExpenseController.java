package Project101.Splitwise.code;

import java.util.List;

public class ExpenseController {
    BalanceSheetController balanceSheetController;
    public ExpenseController (){
        this.balanceSheetController = new BalanceSheetController();
    }
    Expense createExpense(double amount, User paidBy , List<Split> userSplit, SplitType splitType) {
        Validator validator = SplitFactory.getSplitValidator(splitType);
        boolean valid = validator.validate(userSplit,amount);
        if (!valid) {
            return null;
        }
        Expense expense = new Expense(amount,paidBy,userSplit,splitType);
        this.balanceSheetController.updateBalanceSheet(amount,paidBy,userSplit);
        return expense;
    }
}
