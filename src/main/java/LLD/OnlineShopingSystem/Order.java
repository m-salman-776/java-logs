package LLD.OnlineShopingSystem;



import LLD.OnlineShopingSystem.Common.OrderStatus;

import java.util.Date;
import java.util.List;

public class Order {
    String orderNumber;
    OrderStatus status;
    Date orderDate;
    List<OrderLog> orderLogs;
    boolean sendForShipment(){
        return true;
    }
    boolean makePayment(Payment payment){
        return true;
    }
    boolean addOrderLog(OrderLog orderLog){
        this.orderLogs.add(orderLog);
        return true;
    }
}
