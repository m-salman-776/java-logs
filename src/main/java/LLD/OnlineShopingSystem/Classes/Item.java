package LLD.OnlineShopingSystem.Classes;

public class Item {
    int quantity;
    double price;
    boolean updateQuantity(int quantity){
        this.quantity += quantity;
        return true;
    }
}
