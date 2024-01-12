package LLD.Splitwise.code.ValidateImpl;



import LLD.Splitwise.code.Split;
import LLD.Splitwise.code.Validator;

import java.util.List;

public class ValidateUnEqual implements Validator {
    @Override
    public boolean validate(List<Split> splitList, double total) {
        return false;
    }
}
