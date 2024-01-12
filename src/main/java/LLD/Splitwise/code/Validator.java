package LLD.Splitwise.code;

import java.util.List;

public interface Validator {
    boolean validate(List<Split> splitList, double total);
}
