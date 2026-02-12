package Project101.LoyaltyProgram;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Benefits {
    double discount;
    boolean freeBreakfast;
    boolean roomUpgrade;
    public Benefits(){
        discount = 0;
        freeBreakfast = false;
        roomUpgrade = false;
    }
    public synchronized void updateBenefits(Benefits benefits){
        this.discount = benefits.getDiscount();
        this.roomUpgrade = benefits.isRoomUpgrade();
        this.freeBreakfast = benefits.isFreeBreakfast();
    }
}
