package com.factory.PizzaImpl;

import com.factory.Pizza;

public class NYStyleCheesePizza extends Pizza {
    public NYStyleCheesePizza(){
        this.name = "NY Style Cheese Pizza ";
//        this.dough = "Thick Crust ";
//        this.sauce = "NY Sauce";
//        this.toppings.add("Topping 1");
//        this.toppings.add("Topping 2");
//        this.toppings.add("Topping 3");
    }
    @Override
    public void cut(){
        System.out.println("Cutting the pizza in NY Style");
    }
}
