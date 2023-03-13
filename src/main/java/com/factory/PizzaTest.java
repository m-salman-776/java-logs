package com.factory;

import com.factory.IngredientFactoryImpl.ChicagoPizzaIngredientFactory;
import com.factory.PizzaStoreImpl.ChicagoStylePizzaStore;
import com.factory.PizzaStoreImpl.NYStylePizzaStore;

public class PizzaTest {
    public static void main(String args[]){
        PizzaStore chicagoPizzaStore = new ChicagoStylePizzaStore(new ChicagoPizzaIngredientFactory());
        chicagoPizzaStore.orderPizza(PizzaType.CHEESE);
        PizzaStore pizzaStore = new NYStylePizzaStore();
        pizzaStore.orderPizza(PizzaType.CHEESE);
    }
}
