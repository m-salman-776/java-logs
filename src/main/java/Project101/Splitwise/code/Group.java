package Project101.Splitwise.code;

import java.util.ArrayList;
import java.util.List;

public class Group {
    long id;
    String title;
    List<User> userList;
    List<Expense> expenseList;
    ExpenseController expenseController;
    public Group(String title){
        this.title = title;
        userList = new ArrayList<User>();
        expenseList = new ArrayList<Expense>();
        expenseController = new ExpenseController(); // todo could be done better to via dependency injection
    }
    public void addMember(User user){
        this.userList.add(user);
    }
    public void addExpense(Expense expense){
        this.expenseList.add(expense);
    }
    public long getId(){
        return this.id;
    }
    public Expense createExpense(double expenseAmount, List<Split> splitDetails, SplitType splitType, User paidByUser) {
        Expense expense = expenseController.createExpense(expenseAmount, paidByUser,splitDetails, splitType);
        expenseList.add(expense);
        return null;
    }

}
