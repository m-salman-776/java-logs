package Project101.Splitwise.code;


import Project101.Splitwise.code.ValidateImpl.NoValidation;
import Project101.Splitwise.code.ValidateImpl.ValidateEqual;
import Project101.Splitwise.code.ValidateImpl.ValidatePercentage;
import Project101.Splitwise.code.ValidateImpl.ValidateUnEqual;

public class SplitFactory {
    public static Validator getSplitValidator(SplitType splitType) {
        switch (splitType) {
            case EQUAL : return new ValidateEqual();
            case UNEQUAL:return new ValidateUnEqual();
            case PERCENTAGE:return new ValidatePercentage();
            default:return new NoValidation();
        }
    }
}
