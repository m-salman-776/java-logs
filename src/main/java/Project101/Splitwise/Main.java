package Project101.Splitwise;
import Project101.Splitwise.Services.ExpenseService;
import Project101.Splitwise.Services.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.*;
public class Main {
    public static void main(String []args){
        UserService userService = new UserService();
        userService.addUser("alice");// 1
        userService.addUser("bob"); // 2
        userService.addUser("kiran"); //3

        ExpenseService expenseService = new ExpenseService(userService);
        Expense expense01 = new Expense(1,"expense01",123, List.of(1,2,3));
        Expense expense02 = new Expense(2,"expense02",246, List.of(2,3));
        expenseService.addExpense(expense01, SplitType.EQUAL);
        expenseService.addExpense(expense02, SplitType.EQUAL);
        System.out.println("done");
        userService.getUserMap().get(1).checkBalance();
        @Getter
        @AllArgsConstructor
        class ExpenseSimple {
            int userId;
            double amount;
        }
        PriorityQueue<ExpenseSimple> minHeap = new PriorityQueue<>(Comparator.comparingDouble(a -> a.amount));
        PriorityQueue<ExpenseSimple> maxHeap = new PriorityQueue<>((a,b) -> Double.compare(b.amount,a.amount));
        for (User user : userService.getUserMap().values()){
            if (user.getNetBalance() < 0){
                minHeap.add(new ExpenseSimple(user.getUserId(),user.getNetBalance()));
            }else {
                maxHeap.add(new ExpenseSimple(user.getUserId(),user.getNetBalance()));
            }
        }
        HashMap<Integer,List<ExpenseSimple>> simplified = new HashMap<>();
        while (!maxHeap.isEmpty() && !minHeap.isEmpty()){
            ExpenseSimple creditor = maxHeap.poll();
            ExpenseSimple debtor = minHeap.poll();
            double amount = Math.min(creditor.getAmount(), Math.abs(debtor.getAmount()));
            creditor.amount -= amount;
            debtor.amount += amount;
            simplified.computeIfAbsent(debtor.getUserId(),k -> new ArrayList<>());
            simplified.get(debtor.getUserId()).add(new ExpenseSimple(creditor.userId, amount));
            if (creditor.amount > 0){
                maxHeap.add(creditor);
            }
            if (debtor.amount < 0){
                minHeap.add(debtor);
            }
        }
        simplified.forEach((borrower,payers) -> {
            System.out.println("User : " + borrower + "pays to following : ");
            for (ExpenseSimple expenseSimple : payers){
                System.out.println("User : " + expenseSimple.getUserId() + "Amount : "+expenseSimple.getAmount());
            }
            System.out.println();
        });
        System.out.println("done");
    }
}
