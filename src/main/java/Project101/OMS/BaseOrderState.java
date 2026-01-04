package Project101.OMS;

public class BaseOrderState implements OrderState{
    @Override
    public boolean reserveInventory(OrderContext context) {
        throw new IllegalStateException("Action 'Reserve Inventory' not allowed in " + this.getClass().getSimpleName());
    }

    @Override
    public boolean confirmPayment(OrderContext context) {
        throw new IllegalStateException("Action 'Reserve Inventory' not allowed in " + this.getClass().getSimpleName());
    }

    @Override
    public boolean shipOrder(OrderContext context) {
        throw new IllegalStateException("Action 'Reserve Inventory' not allowed in " + this.getClass().getSimpleName());
    }

    @Override
    public boolean cancelOrder(OrderContext context) {
        throw new IllegalStateException("Action 'Reserve Inventory' not allowed in " + this.getClass().getSimpleName());
    }
}
