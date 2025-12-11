package Project101.Splitwise;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Split {
    int userId;
    double amount ;
    public Split(int userId,double amount){
        this.amount = amount;
        this.userId = userId;
    }
}
