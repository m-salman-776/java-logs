package com.factory.PizzaImpl;

import com.factory.Pizza;
import com.factory.PizzaIngredientFactory;

public class ChicagoStyleCheesePizza extends Pizza {
    PizzaIngredientFactory pizzaIngredientFactory;
    public ChicagoStyleCheesePizza(PizzaIngredientFactory pizzaIngredientFactory){
        this.name = "Chicago Style Cheese Pizza ";
        this.pizzaIngredientFactory = pizzaIngredientFactory;
//        this.dough = "Thin Crust ";
//        this.sauce = "Chicago Sauce";
//        this.toppings.add("Topping 1");
//        this.toppings.add("Topping 2");
//        this.toppings.add("Topping 3");
    }

    @Override
    protected void prepare() {
        this.sauce = pizzaIngredientFactory.createSauce();
        this.dough = pizzaIngredientFactory.createDough();
    }

    @Override
    public void cut(){
        System.out.println("Cutting the pizza in Chicago Style");
    }
}
