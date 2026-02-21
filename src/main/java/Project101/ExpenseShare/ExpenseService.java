package Project101.ExpenseShare;

import Project101.ExpenseShare.SplitStrategies.StrategyFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ExpenseService {
    // expendId -> Expense;
    private final Map<Integer,Expense> expenseMap;
    // expenseId -> List[ExpenseSplit]
    private final Map<Integer,List<ExpenseSplit>> expenseSplitMap;
    private final AtomicInteger expenseIdGenerator ;
    
    // BorrowerID -> (LenderID -> Amount)
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
            return;
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
                balanceSheet.computeIfAbsent(split.userId, k -> new ConcurrentHashMap<>())
                        .merge(payerId, amount, Double::sum);
            }
        }
    }
    public void checkBalance(int userId){
        Map<Integer, Double> owes = balanceSheet.getOrDefault(userId, new HashMap<>());
        for (Map.Entry<Integer, Double> entry : owes.entrySet()) {
            if (entry.getValue() > 0.01) {
                System.out.printf("You owe User %d: %.2f\n", entry.getKey(), entry.getValue());
            }
        }

        for (Map.Entry<Integer, Map<Integer, Double>> entry : balanceSheet.entrySet()) {
            int otherUser = entry.getKey();
            if (otherUser == userId) continue;
            Double amount = entry.getValue().get(userId);
            if (amount != null && amount > 0.01) {
                System.out.printf("User %d owes you: %.2f\n", otherUser, amount);
            }
        }
    }
    public double checkNetBalance(int userId){
        double net = 0;
        // + what others owe me
        for (Map.Entry<Integer, Map<Integer, Double>> entry : balanceSheet.entrySet()) {
            Double amount = entry.getValue().get(userId);
            if (amount != null) net += amount;
        }
        // - what I owe others
        Map<Integer, Double> myDebts = balanceSheet.get(userId);
        if (myDebts != null) {
            for (double val : myDebts.values()) net -= val;
        }
        System.out.printf("Net Balance for User %d: %.2f\n", userId, net);
        return net;
    }
    public void debtSimplification(){
        Map<Integer, Double> netBalance = new HashMap<>();
        for (Map.Entry<Integer, Map<Integer, Double>> debtorEntry : balanceSheet.entrySet()) {
            int debtor = debtorEntry.getKey();
            for (Map.Entry<Integer, Double> creditorEntry : debtorEntry.getValue().entrySet()) {
                int creditor = creditorEntry.getKey();
                double amount = creditorEntry.getValue();
                netBalance.put(debtor, netBalance.getOrDefault(debtor, 0.0) - amount);
                netBalance.put(creditor, netBalance.getOrDefault(creditor, 0.0) + amount);
            }
        }

        PriorityQueue<Map.Entry<Integer, Double>> debtors = new PriorityQueue<>(Map.Entry.comparingByValue());
        PriorityQueue<Map.Entry<Integer, Double>> creditors = new PriorityQueue<>((a, b) -> Double.compare(b.getValue(), a.getValue()));

        for (Map.Entry<Integer, Double> entry : netBalance.entrySet()) {
            if (entry.getValue() < -0.01) debtors.add(entry);
            else if (entry.getValue() > 0.01) creditors.add(entry);
        }

        while (!debtors.isEmpty() && !creditors.isEmpty()) {
            Map.Entry<Integer, Double> debtor = debtors.poll();
            Map.Entry<Integer, Double> creditor = creditors.poll();

            double debt = Math.abs(debtor.getValue());
            double credit = creditor.getValue();
            double settleAmount = Math.min(debt, credit);

            System.out.printf("User %d pays User %d: %.2f\n", debtor.getKey(), creditor.getKey(), settleAmount);

            double remainingDebt = debt - settleAmount;
            double remainingCredit = credit - settleAmount;

            if (remainingDebt > 0.01) {
                debtor.setValue(-remainingDebt);
                debtors.add(debtor);
            }
            if (remainingCredit > 0.01) {
                creditor.setValue(remainingCredit);
                creditors.add(creditor);
            }
        }
    }
}
