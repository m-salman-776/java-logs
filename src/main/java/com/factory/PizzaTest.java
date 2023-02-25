package com.factory;

import com.factory.PizzaStoreImpl.NYStylePizzaStore;

public class PizzaTest {
    public static void main(String args[]){
        PizzaStore pizzaStore = new NYStylePizzaStore();
        pizzaStore.orderPizza();
    }
}
