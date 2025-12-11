package Project101.OMS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class Order {
    int orderId;
    int userId;
    double totalAmount;
    List<OrderItem> orderItemList;
    OrderStatus status;
    String address;
    int version;
}
