package Project101.Splitwise.code.ValidateImpl;




import Project101.Splitwise.code.Split;
import Project101.Splitwise.code.Validator;

import java.util.List;

public class NoValidation implements Validator {
    @Override
    public boolean validate(List<Split> splitList, double total) {
        return true;
    }
}
