package Project101.Splitwise.code;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Split {
    User user;
    double amount ;
    public Split(User user,double amount){
        this.amount = amount;
        this.user = user;
    }
}
