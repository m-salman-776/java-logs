package LLD.OnlineShopingSystem;

import LLD.OnlineShopingSystem.Classes.Product;

import java.util.List;

public interface Search {
    List<Product> searchByName(String name);
    List<Product> searchByCategory(String category);
}
