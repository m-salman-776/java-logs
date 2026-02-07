package Project101.OrderInventory;

public class Order {
    public int id;
    public int productId;
    public int quantity;
    public int userId;
    public OrderStatus orderStatus;
    public Order(int id, int userId,int productId,int quantity){
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.orderStatus = OrderStatus.CREATED;
    }
    public void updateOrderStatus(OrderStatus status){
        this.orderStatus = status;
    }
}
