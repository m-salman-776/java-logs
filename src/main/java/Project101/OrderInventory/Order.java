package Project101.OrderInventory;

public class Order {
    public int id;
    public int productId;
    public int quantity;
    public int userId;
    public Order(int id, int userId,int productId,int quantity){
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
