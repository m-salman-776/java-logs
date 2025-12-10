package Assingment;

import java.util.List;

public interface SplitStrategy {
    List<Split> split(double amount, User paidBy ,List<User> participant);
}
