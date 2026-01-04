package Project101.OMS;

public interface OrderState {
    boolean reserveInventory(OrderContext context);
    boolean confirmPayment(OrderContext context);
    boolean shipOrder(OrderContext context);
    boolean cancelOrder(OrderContext context);
}
