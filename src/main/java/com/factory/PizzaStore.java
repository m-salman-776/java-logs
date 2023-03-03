package com.factory;

public abstract class PizzaStore {

   protected abstract Pizza createPizza(PizzaType type);
    public void orderPizza(){
        Pizza pizza;
        pizza = createPizza(PizzaType.CHEESE);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
    }
}
