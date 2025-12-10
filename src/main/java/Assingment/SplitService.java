package Assingment;

import java.util.*;

public class SplitService {
    Map<Integer,Expense> groupToExpenseMapping;
    Map<String,Transaction> userTransaction;
    Set<String> allUser;
    int expenseId ;
    public SplitService(){
        this.groupToExpenseMapping = new HashMap<>();
        this.expenseId = 0;
        this.userTransaction = new HashMap<>();
        this.allUser = new HashSet<>();
    }
    public void addExpense(User paidBy, double amount, int number,List<String> friendList, SplitType splitType,List<Double> split){

    }
    public void addExpense(User paidBy, double amount, int number,List<String> friendList){

        List<User> userList = new ArrayList<>();
        for (int idx=0;idx++ < number;idx++){
            String userName = friendList.get(idx);
            User borrower = new User(userName);
            userList.add(borrower);
        }
        SplitStrategy splitStrategy = SplitFactory.getSplitSplitFactory(SplitType.EQUAL);
        userList.add(paidBy);
        List<Split> splitList = splitStrategy.split(amount,paidBy,userList);
        Expense expense = new Expense(this.expenseId,paidBy,amount,splitList);
        groupToExpenseMapping.put(expense.getId(), expense);

        userTransaction.computeIfAbsent(paidBy.getName(), k -> new Transaction(paidBy));
        for (Split split : splitList){
            userTransaction.get(paidBy.getName()).addSplit(split);
        }

        this.expenseId += 1;
    }
    public void showBalanceForUser(String userName){
        Transaction userTransaction = this.userTransaction.get(userName);
        System.out.println("Transaction for -> "+userName);
        for (Split split : userTransaction.getBorrowers()){
            System.out.println(split.getBorrower().getName() + " owns " + split.getAmount());
        }
    }
    public void showBalanceForAllUser(){
        for (String userName : this.allUser){
            this.showBalanceForUser(userName);
        }
    }
    public void settleDebtOptimally(){

    }

//    ADD_EXPENSE Charlie 2400 3 Alice David Charlie PERCENT 40 20 40
//    ADD_EXPENSE Eve 900 3 Eve Alice Bob EQUAL
//    ADD_EXPENSE Frank 1800 2 Frank David EXACT 0 1800

}
