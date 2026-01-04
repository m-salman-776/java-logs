package Project101.Splitwise;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
@Getter
public class User {
    int userId;
    String name;
    double netBalance;
    Map<Integer,Double> transaction;
    public User(int userId,String name){
        this.name = name;
        this.userId = userId;
        this.netBalance = 0;
        this.transaction = new HashMap<>();
    }
    public void summariseBalance(){
        this.netBalance = 0;
        transaction.forEach((key,val) ->{
            this.netBalance+= val;
        });
    }
    public void addTransaction(int userId, double amount){
        // amount can be +,-
        transaction.put(userId,transaction.getOrDefault(userId,0.0)+amount);
        this.netBalance += amount;
    }
    public void addPayerTransaction(double amount){
        this.transaction.put(this.userId,transaction.getOrDefault(this.userId,0.0)+amount);
    }
    public double checkBalance(){
        System.out.println(this.name +" 's Balance sheet");
        transaction.forEach((key,val) -> {
            if (key == this.userId) return;
            System.out.println(key + " -> " + val);
        });
        summariseBalance();
        System.out.println(this.name +" 's Balance sheet Summary " + this.netBalance);
        System.out.println("======================================================");
        return this.netBalance;
    }
}
