//package Project101.Splitwise.Services;
//
//import Project101.Splitwise.Expense;
//import Project101.Splitwise.Repositories.ExpenseRepository;
//
//public class DebtSimplificationService {
//    ExpenseRepository expenseRepository;
//    public DebtSimplificationService(){
//        expenseRepository = new ExpenseRepository();
//    }
//    List<SettlementTransaction> simplifyDebts(String groupId) {
//        List<Expense> expenseList = expenseRepository.getExpense(groupId);
//        Map<String,Double> expenseMap = new HashMap<>();
//        expenseList.forEach(expense -> {
//            expenseMap.merge(expense.getPaidBy().getUserName(),expense.getAmount(),Double::sum);
//            expenseMap.put(expense.getPaidBy().getUserName(),expense.getAmount());
//            expense.getSplitList().forEach(split -> {
//                expenseMap.merge(split.getUser().getUserName(),split.getAmount(),Double::sum);
//            });
//        });
//
//        PriorityQueue<ExpenseNode> creditorMaxHeap = new PriorityQueue<>((a,b) -> Double.compare(b.amount,a.amount));
//        PriorityQueue<ExpenseNode> debtorMaxHeap = new PriorityQueue<>((a,b) -> Double.compare(b.amount,a.amount));
//        expenseMap.forEach((userId,amount) -> {
//            if (amount > 0){
//                creditorMaxHeap.add(new ExpenseNode(userId,amount));
//            }else{
//                debtorMaxHeap.add(new ExpenseNode(userId,amount));
//            }
//        });
//        List<SettlementTransaction> settlementTransactions = new ArrayList<>();
//        while (!creditorMaxHeap.isEmpty() && !debtorMaxHeap.isEmpty()) {
//            ExpenseNode creditor = creditorMaxHeap.poll();
//            ExpenseNode debtor = debtorMaxHeap.poll();
//            assert debtor != null;
//            double amount = Math.min(Math.abs(debtor.getAmount()),creditor.amount);
//            settlementTransactions.add(new SettlementTransaction(debtor.getUserName(),creditor.getUserName(),amount));
//            debtor.amount -= amount;
//            creditor.amount -= amount;
//            if (creditor.amount > 0.01) {
//                creditorMaxHeap.add(creditor);
//            }
//            if (Math.abs(debtor.amount) > 0.01){
//                debtorMaxHeap.add(debtor);
//            }
//        }
//        return settlementTransactions;
//    }
//    @Getter
//    class ExpenseNode {
//        String userName;
//        double amount;
//        public ExpenseNode(String userName, double amount){
//            this.userName = userName;
//            this.amount = amount;
//        }
//    }
//}
