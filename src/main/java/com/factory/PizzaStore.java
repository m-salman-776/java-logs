package com.factory;

public abstract class PizzaStore {

   protected abstract Pizza createPizza(PizzaType type);
    public void orderPizza(PizzaType pizzaType){
        Pizza pizza;
        pizza = createPizza(pizzaType);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
    }
}
