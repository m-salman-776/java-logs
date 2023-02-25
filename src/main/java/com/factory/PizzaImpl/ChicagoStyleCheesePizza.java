package com.factory.PizzaImpl;

import com.factory.Pizza;

public class ChicagoStyleCheesePizza extends Pizza {
    public ChicagoStyleCheesePizza(){
        this.name = "Chicago Style Cheese Pizza ";
        this.dough = "Thin Crust ";
        this.sauce = "Chicago Sauce";
        this.toppings.add("Topping 1");
        this.toppings.add("Topping 2");
        this.toppings.add("Topping 3");
    }
    @Override
    public void cut(){
        System.out.println("Cutting the pizza in Chicago Style");
    }
}
