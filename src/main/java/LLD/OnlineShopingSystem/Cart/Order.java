package LLD.OnlineShopingSystem.Cart;

import com.mysql.cj.exceptions.StreamingNotifiable;

public class Order {
    int id;
    int userId;
    float price;
    String paymentStatus;
}

/*
    create table Order(
        id int,
        user_id int
        price float
        payment_status
        payment_id
    )
* */