package LLD.Splitwise.code.ValidateImpl;



import LLD.Splitwise.code.Split;
import LLD.Splitwise.code.Validator;

import java.util.List;

public class ValidateEqual implements Validator {
    public ValidateEqual(){
    }
    @Override
    public boolean validate(List<Split> splitList, double total) {
        if (splitList.size() == 0) {
            return  false;
        }
        double totalSum = 0.0;
        double individualShare = total / splitList.size();
        for (Split split : splitList){
            if (split.getAmount() != individualShare) { // TODO need more elegant double comparison (i.e precision)
                return false;
            }
            totalSum += split.getAmount();
        }
        if (total != totalSum) { // TODO need more elegant double comparison (i.e precision)
            return false;
        }
        return true;
    }
}
