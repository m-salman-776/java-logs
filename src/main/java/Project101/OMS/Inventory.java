package Project101.OMS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inventory {
    int productId;
    int availableStock;
    int reservedStock;
    int version;
    public Inventory(int productId,int availableStock){
        this.productId = productId;
        this.availableStock = availableStock;
        this.version = 0;
        this.reservedStock = 0;
    }
}
