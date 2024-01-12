package LLD.OnlineShopingSystem;

import java.util.List;

public interface Search {
    List<Product> searchByName(String name);
    List<Product> searchByCategory(String category);
}
