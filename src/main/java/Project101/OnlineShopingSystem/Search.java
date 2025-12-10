package Project101.OnlineShopingSystem;

import Project101.OnlineShopingSystem.Classes.Product;

import java.util.List;

public interface Search {
    List<Product> searchByName(String name);
    List<Product> searchByCategory(String category);
}
