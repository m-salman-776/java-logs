package DesignPatterns.factory;

import DesignPatterns.factory.IngredientFactoryImpl.ChicagoPizzaIngredientFactory;
import DesignPatterns.factory.PizzaStoreImpl.ChicagoStylePizzaStore;
import DesignPatterns.factory.PizzaStoreImpl.NYStylePizzaStore;

public class PizzaTest {
    public static void main(String args[]){
        PizzaStore chicagoPizzaStore = new ChicagoStylePizzaStore(new ChicagoPizzaIngredientFactory());
        chicagoPizzaStore.orderPizza(PizzaType.CHEESE);
        PizzaStore pizzaStore = new NYStylePizzaStore();
        pizzaStore.orderPizza(PizzaType.CHEESE);
    }
}
