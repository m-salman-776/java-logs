package LLD.Splitwise.code;


import LLD.Splitwise.code.ValidateImpl.NoValidation;
import LLD.Splitwise.code.ValidateImpl.ValidateEqual;
import LLD.Splitwise.code.ValidateImpl.ValidatePercentage;
import LLD.Splitwise.code.ValidateImpl.ValidateUnEqual;

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
