package com.factory;

public abstract class PizzaStore {
    Pizza pizza;
   protected abstract Pizza createPizza(PizzaType type);
    public void orderPizza(){
        pizza = createPizza(PizzaType.CHEESE);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
    }
}
