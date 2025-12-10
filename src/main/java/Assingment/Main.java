package Assingment;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String []args){
        SplitService splitService = new SplitService();
        User payer = new User("Alice");
        List<String> userNames = new ArrayList<>();
        userNames.add("Bob");
        userNames.add("David");
        splitService.addExpense(payer,10.0,2,userNames);
        splitService.showBalanceForUser("Alice");
        System.out.println("DONE");
    }
}
