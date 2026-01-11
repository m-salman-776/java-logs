package Project101.OMS.OrderStates;

import Project101.OMS.OrderContext;

public class ReservedState extends BaseOrderState {
    @Override
    public boolean confirmPayment(OrderContext context) {
//        return super.confirmPayment(context);
        return true;
    }
}
