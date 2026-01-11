package Project101.OMS.OrderStates;

import Project101.OMS.OrderContext;

public interface OrderState {
    boolean reserveInventory(OrderContext context);
    boolean confirmPayment(OrderContext context);
    boolean shipOrder(OrderContext context);
    boolean cancelOrder(OrderContext context);
}
