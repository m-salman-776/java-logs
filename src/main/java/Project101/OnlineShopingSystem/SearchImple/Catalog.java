package Project101.OnlineShopingSystem.SearchImple;



import Project101.OnlineShopingSystem.Classes.Product;
import Project101.OnlineShopingSystem.Search;

import java.util.HashMap;
import java.util.List;

public class Catalog implements Search {
    HashMap<String,List<Product>> productNames;
    HashMap<String,List<Product>> productCategory;
    @Override
    public List<Product> searchByName(String name) {
        return this.productNames.get(name);
    }

    @Override
    public List<Product> searchByCategory(String category) {
        return this.productCategory.get(category);
    }
}
