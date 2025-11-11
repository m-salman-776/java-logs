package Common;

import lombok.Getter;

public class Account {
    String accountId;
    private String password;
    @Getter
    private double balance;

    public Account(String accountId, double initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
    }

    public boolean resetPassword(String newPassword){
        this.password = newPassword;
        return  true;
    }

    public void debit(double amount) throws Exception {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Debited " + amount + " from " + accountId + ". New balance: " + balance);
        } else {
            throw new Exception("Insufficient funds in " + accountId);
        }
    }

    public void credit(double amount) throws Exception {
        balance += amount;
        System.out.println("Credited " + amount + " to " + accountId + ". New balance: " + balance);
    }
}
