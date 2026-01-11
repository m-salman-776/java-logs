package Project101.OMS.OrderStates;

import Project101.OMS.OrderContext;

public class CreatedState extends BaseOrderState {
    @Override
    public boolean reserveInventory(OrderContext context) {
        boolean success = context.getInventoryService().reserveStock(context.getOrder().getOrderItemList());
        if (success){
            context.setOrderState(new PaymentPendingState());
        }
        return true;
    }
}
