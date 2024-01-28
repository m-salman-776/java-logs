package LLD.OnlineShopingSystem.Classes;

import LLD.OnlineShopingSystem.Classes.Item;

import java.util.List;

public class ShoppingCart {
    List<Item> items;
    boolean addItem(Item item){
      return true;
    };
    boolean removeItem(Item item){
        return true;
    }
    List<Item> getItems(){
        return this.items;
    }
    boolean updateQuantity(Item item,int quantity){
        return true;
    }
    boolean checkOut(){
        return true;
    }
}
