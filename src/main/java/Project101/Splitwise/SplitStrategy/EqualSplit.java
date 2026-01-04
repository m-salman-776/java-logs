package Project101.Splitwise.SplitStrategy;

import Project101.Splitwise.Expense;
import Project101.Splitwise.Split;

import java.util.ArrayList;
import java.util.List;

public class EqualSplit implements SplitStrategy {
    @Override
    public List<Split> calculateSplits(Expense request) {
        double amount = request.getAmount();
//        Split self = new Split(request.getPayerId(),0);
        List<Split> splitList = new ArrayList<>();
        for (int userId : request.getParticipants()){
            double share = Math.floor(((double) request.getAmount() / request.getParticipants().size()) * 100) / 100.0;
            splitList.add(new Split(userId,share));
            amount -= share;
        }
        if (amount > 0.8){
            splitList.get(0).setAmount(splitList.get(0).getAmount()+amount);
        }
        return splitList;
    }
}
