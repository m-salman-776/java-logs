package Project101.Splitwise.code;

import java.util.HashMap;
import java.util.List;

public class BalanceSheetController {
    public void updateBalanceSheet(double amount, User paidByUser , List<Split> userSplit){
        BalanceSheet currentUsersBalanceSheet = paidByUser.getBalanceSheet();
        currentUsersBalanceSheet.addTotalGetBackAmount(amount); // TODO will remove extra amount;
        currentUsersBalanceSheet.addTotalExpenditure(amount);
        for (Split split : userSplit){
            User splitUser = split.getUser();
            BalanceSheet splitUserBalanceSheet = splitUser.getBalanceSheet();
            if (splitUser.getUserId() == paidByUser.getUserId()){
                currentUsersBalanceSheet.addTotalExpenditure(-split.getAmount()); // TODO removed extra amount
            }
            Balance balance;
            if (paidByUser.getBalanceSheet().getExpenseLines().containsKey(splitUser.getUserId()+"!")){ // todo just hack will fix
                // this user was already in balanceSheet only update
                balance = splitUser.getBalanceSheet().getExpenseLines().get(splitUser.getUserId()+"!");
            }else {
                // user was not already exited hence
                balance = new Balance();
                paidByUser.getBalanceSheet().getExpenseLines().put(splitUser.getUserId()+"!",balance);
            }
//            splitUser.getBalanceSheet().se
        }
    }

    public void displayBalance(User user){
        System.out.println("---------------------------------------");

        System.out.println("Balance sheet of user : " + user.getUserId());

        BalanceSheet userExpenseBalanceSheet =  user.getBalanceSheet();

        System.out.println("TotalExpense: " + userExpenseBalanceSheet.getTotalExpenditure());
        System.out.println("TotalGetBack: " + userExpenseBalanceSheet.getTotalGetBackAmount());
        System.out.println("TotalYourOwe: " + userExpenseBalanceSheet.getTotalOweAmount());
        for(HashMap.Entry<String, Balance> entry : userExpenseBalanceSheet.getExpenseLines().entrySet()){

            String userID = entry.getKey();
            Balance balance = entry.getValue();

            System.out.println("userID:" + userID + " YouGetBack:" + balance.getBackAmount() + " YouOwe:" + balance.getOweAmount());
        }

        System.out.println("---------------------------------------");

    }
}
