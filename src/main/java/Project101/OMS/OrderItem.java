package Project101.OMS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderItem {
    int productId;
    int quantity;
    String name;
    double price;

}
