package Project101.Splitwise.V2;

import Project101.Splitwise.code.Split;
import Project101.Splitwise.code.SplitFactory;
import Project101.Splitwise.code.User;

import java.util.ArrayList;
import java.util.List;

public class EqualSplit implements SplitStrategy{
    @Override
    public List<Split> calculateSplits(ExpenseRequest request) {
        double amount = request.getAmount();
        List<Split> splitList = new ArrayList<>();
        for (User user : request.getUserList()){
            splitList.add(new Split(user,amount/request.getUserList().size()));
            amount -= amount/request.getUserList().size();
        }
        if (amount > 0){
            splitList.get(0).setAmount(splitList.get(0).getAmount()+amount);
        }
        return splitList;
    }
}
