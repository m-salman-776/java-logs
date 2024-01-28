package LLD.OnlineShopingSystem.Cart;

public class CartItem {
    int id;
    int productId;
    int cartId;
    int quantity;
    float totalPrice; // unit * quantity

    boolean isPurchased;

    public CartItem(int productId,int quantity,float unitPrice){
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = unitPrice * quantity;
        this.isPurchased = true;
    }

    public void updateIsPurchased(boolean isPurchased){
        this.isPurchased = isPurchased;
    }
    public void updateQuantity(int quantity){
        this.quantity = quantity;
    }
}

/*
create table CartItem (
    id int,
    cart_id int,
    product_id int,
    quantity int
    price float;
)
* */
