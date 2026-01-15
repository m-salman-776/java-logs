package Project101.OrderInventory;

public class ProductInventory {
    public int productId;
    public int quantity;
    public int reservedQuantity;
    public ProductInventory(Product product, int quantity){
        this.productId = product.id;
        this.quantity = quantity;
        this.reservedQuantity = 0;
    }

}
