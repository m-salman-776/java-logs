package LLD.OnlineShopingSystem.Classes;

public class Product {
    String name;
    String description;
    double price;
    ProductCategory productCategory;
    int availableCount ;
    boolean updatePrice(double newPrice){
        this.price = newPrice ;
        return true;
    }
}
