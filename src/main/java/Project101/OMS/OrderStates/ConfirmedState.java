package Project101.OMS.OrderStates;

import Project101.OMS.BaseOrderState;
import Project101.OMS.OrderContext;

public class ConfirmedState extends BaseOrderState {
    @Override
    public boolean cancelOrder(OrderContext context) {
        return super.cancelOrder(context);
    }

    @Override
    public boolean shipOrder(OrderContext context) {
        return super.shipOrder(context);
    }
}
