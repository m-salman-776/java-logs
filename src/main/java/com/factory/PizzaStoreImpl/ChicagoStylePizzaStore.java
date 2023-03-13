package com.factory.PizzaStoreImpl;

import com.factory.IngredientFactoryImpl.ChicagoPizzaIngredientFactory;
import com.factory.Pizza;
import com.factory.PizzaImpl.ChicagoStyleCheesePizza;
import com.factory.PizzaImpl.ChicagoStyleClamPizza;
import com.factory.PizzaIngredientFactory;
import com.factory.PizzaStore;
import com.factory.PizzaType;
import com.factory.ingredients.*;

public class ChicagoStylePizzaStore extends PizzaStore {
    PizzaIngredientFactory pizzaIngredientFactory;
    public ChicagoStylePizzaStore(PizzaIngredientFactory pizzaIngredientFactory){
        this.pizzaIngredientFactory = pizzaIngredientFactory;
    }
    @Override
   protected Pizza createPizza(PizzaType type) {
//        PizzaIngredientFactory pizzaIngredientFactory = new ChicagoPizzaIngredientFactory();
        if (type == PizzaType.CHEESE){
            return new ChicagoStyleCheesePizza(pizzaIngredientFactory);
        }else {
            return new ChicagoStyleClamPizza();
        }
    }

    // Cheese
    // Veggie
    // Clam
    // Pepperoni
}
