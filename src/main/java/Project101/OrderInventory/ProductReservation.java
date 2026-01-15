package Project101.OrderInventory;

import java.time.Instant;

public class ProductReservation {
    public int userId;
    public int productId;
    public int quantity;
    public long reservedAt ;
    public ProductReservation(int userId, int productId,int quantity){
        this.productId = productId;
        this.userId = userId;
        this.quantity = quantity;
        this.reservedAt = Instant.now().getEpochSecond();
    }
}
