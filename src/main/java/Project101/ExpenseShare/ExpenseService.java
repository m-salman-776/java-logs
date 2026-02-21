package Project101.ExpenseShare;

import Project101.ExpenseShare.SplitStrategies.StrategyFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ExpenseService {
    // expendId -> Expense;
    private final Map<Integer,Expense> expenseMap;
    // expenseId -> List[ExpenseSplit]
    private final Map<Integer,List<ExpenseSplit>> expenseSplitMap;
    private final AtomicInteger expenseIdGenerator ;
    
    // LenderID -> (BorrowerID -> Amount)
    private final Map<Integer,Map<Integer,Double>> balanceSheet;
    public ExpenseService(){
        expenseIdGenerator = new AtomicInteger(1);
        expenseMap = new ConcurrentHashMap<>();
        expenseSplitMap = new ConcurrentHashMap<>();
        balanceSheet = new ConcurrentHashMap<>();
    }
    public void addExpense(int payerId,double totalAmount,String description,SplitType type,Map<Integer,Double> splits){
        int expendId = expenseIdGenerator.getAndIncrement();
        Expense expense = new Expense(expendId,payerId,totalAmount,description,type,splits);
        expenseMap.put(expendId,expense);
        List<ExpenseSplit> expenseSplits = StrategyFactory.getSplitStrategy(expense.splitType).calculateSplits(expense);
        expenseSplitMap.put(expendId,expenseSplits);
        updateBalanceSheet(expenseSplits, payerId, true);
    }
    public void deleteExpense(int expenseId){
        Expense expense = expenseMap.get(expenseId);
        if (expense == null) return;
        List<ExpenseSplit> splits = expenseSplitMap.get(expenseId);
        updateBalanceSheet(splits, expense.payerId, false);
        expenseMap.remove(expenseId);
        expenseSplitMap.remove(expenseId);
    }
    public void updateExpense(int expenseId, int payerId, double totalAmount, String description, SplitType type, Map<Integer, Double> splits) {
        if (!expenseMap.containsKey(expenseId)) {
            System.out.println("Expense not found");
            throw  new IllegalArgumentException("ExpenseId Not found");
        }
        deleteExpense(expenseId);
        Expense expense = new Expense(expenseId, payerId, totalAmount, description, type, splits);
        expenseMap.put(expenseId, expense);
        List<ExpenseSplit> expenseSplits = StrategyFactory.getSplitStrategy(expense.splitType).calculateSplits(expense);
        expenseSplitMap.put(expenseId, expenseSplits);
        updateBalanceSheet(expenseSplits, payerId, true);
    }
    private synchronized void updateBalanceSheet(List<ExpenseSplit> splits, int payerId, boolean isAdd){
        for (ExpenseSplit split : splits) {
            if (split.expenseType == ExpenseType.CONSUMER) {
                if (split.userId == payerId) continue;
                // split.userId owes payerId split.netAmount
                double amount = isAdd ? split.netAmount : -split.netAmount;
                balanceSheet.computeIfAbsent(payerId, k -> new ConcurrentHashMap<>()).merge(split.userId, amount, Double::sum);
            }
        }
    }
    
    public double showBalanceSheet(int userId){
        System.out.printf("============================ Start of User's %d Statement ===========================\n",userId);
        double net = 0;
        // + what others owe me
        Map<Integer, Double> myCredits = balanceSheet.get(userId);
        if (myCredits != null) {
            for (Map.Entry<Integer, Double> entry : myCredits.entrySet()) {
                int borrowerId = entry.getKey();
                double amount = entry.getValue();
                if (amount <= 0.01) continue;
                net += amount;
                System.out.printf("User %d owes You : %.2f\n", borrowerId, amount);
            }
        }
        // - what I owe others
        for (Map.Entry<Integer, Map<Integer, Double>> entry : balanceSheet.entrySet()) {
            int lenderId = entry.getKey();
            Double amount = entry.getValue().get(userId);
            if (amount == null || amount <= 0.01) continue;
            System.out.printf("You owe User %d : %.2f\n", lenderId, amount);
            net -= amount;
        }
        System.out.printf("Net Balance for User %d: %.2f\n", userId, net);
        System.out.printf("============================ End of User's %d Statement ===========================\n",userId);
        return net;
    }
    public List<ExpenseTransaction> debtSimplication(){
        Map<Integer,Double> netBalance = new HashMap<>();
        // [lender,[debtor,double]]
        for (Integer lender : balanceSheet.keySet()){
            for (Map.Entry<Integer,Double> entry : balanceSheet.get(lender).entrySet()){
                int borrower = entry.getKey();
                netBalance.put(lender,netBalance.getOrDefault(lender,0.0)+entry.getValue());
                netBalance.put(borrower,netBalance.getOrDefault(borrower,0.0)-entry.getValue());
            }
        }
        PriorityQueue<Map.Entry<Integer,Double>> creditor = new PriorityQueue<>((a,b) -> Double.compare(b.getValue(),a.getValue()));
        PriorityQueue<Map.Entry<Integer,Double>> debtor = new PriorityQueue<>((a,b) -> Double.compare(a.getValue(),b.getValue()));

        for (Map.Entry<Integer,Double> entry : netBalance.entrySet()){
            if (entry.getValue() < -0.01) {
                debtor.add(entry);
            }else if (entry.getValue() > 0.01){
                creditor.add(entry);
            }
        }
        List<ExpenseTransaction> transactions = new ArrayList<>();

        while (!creditor.isEmpty() && !debtor.isEmpty()){
            Map.Entry<Integer,Double> creditorEntry = creditor.poll();
            Map.Entry<Integer,Double> debtorEntry = debtor.poll();


            double debt = Math.abs(debtorEntry.getValue());
            double credit = creditorEntry.getValue();

            double settlementAmount = Math.min(debt,credit);

            double remainingDebt = debt - settlementAmount;
            double remainingCredit = credit - settlementAmount;

            transactions.add(new ExpenseTransaction(debtorEntry.getKey(),creditorEntry.getKey(),settlementAmount));

            if (remainingCredit > 0.01){
                creditorEntry.setValue(remainingCredit);
                creditor.add(creditorEntry);
            }
            if (remainingDebt > 0.01){
                debtorEntry.setValue(-remainingDebt);
                debtor.add(debtorEntry);
            }
        }
        return transactions;
    }
}
