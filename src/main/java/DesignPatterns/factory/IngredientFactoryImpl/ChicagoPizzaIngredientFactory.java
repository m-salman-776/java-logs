package DesignPatterns.factory.IngredientFactoryImpl;

import DesignPatterns.factory.PizzaIngredientFactory;
//import com.factory.ingredients.*;
import DesignPatterns.factory.ingredients.*;

public class ChicagoPizzaIngredientFactory implements PizzaIngredientFactory {
    @Override
    public Dough createDough() {
        return new Dough();
    }

    @Override
    public Sauce createSauce() {
        return new Sauce();
    }

    @Override
    public Cheese createCheese() {
        return new Cheese();
    }

    @Override
    public Clams createClams() {
        return new Clams();
    }

    @Override
    public Veggies[] createVeggies() {
        return new Veggies[0];
    }

    @Override
    public Pepperoni createPepperoni() {
        return new Pepperoni();
    }
}
