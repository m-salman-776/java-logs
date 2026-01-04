package Project101.OMS.Services;

import Project101.OMS.Order;
import Project101.OMS.OrderItem;
import Project101.OMS.OrderStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {
    InventoryService inventoryService;
    PaymentService paymentService;
    Map<Integer,Order> orderMap;
    int orderId;
    public OrderService(){
        this.inventoryService = new InventoryService();
        this.paymentService = new PaymentService();
        this.orderId = 0;
        orderMap = new HashMap<>();
    }
    public int createOrder(int productId, int userId, int quantity,String address){
        this.orderId += 1;
        OrderItem orderItem = new OrderItem(productId,quantity,"some name",124.5);
        double totalAmount = orderItem.getPrice() * orderItem.getQuantity();
        Order order = new Order(this.orderId,userId,totalAmount, List.of(orderItem), OrderStatus.CREATED,address,1);
        // confused of order in which it should happen
        this.orderMap.put(this.orderId,order);
        boolean reserverStock = inventoryService.reserveStock(order.getOrderItemList());
        if (reserverStock){
            order.setStatus(OrderStatus.RESERVED);
        }else{
            order.setStatus(OrderStatus.FAILED);
        }
        return orderId;
    }
}
