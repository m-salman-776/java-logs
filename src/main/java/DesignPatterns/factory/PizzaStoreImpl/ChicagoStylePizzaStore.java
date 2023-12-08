package DesignPatterns.factory.PizzaStoreImpl;

import DesignPatterns.factory.Pizza;
import DesignPatterns.factory.PizzaImpl.ChicagoStyleCheesePizza;
import DesignPatterns.factory.PizzaImpl.ChicagoStyleClamPizza;
import DesignPatterns.factory.PizzaIngredientFactory;
import DesignPatterns.factory.PizzaStore;
import DesignPatterns.factory.PizzaType;
//import com.factory.ingredients.*;

public class ChicagoStylePizzaStore extends PizzaStore {
    PizzaIngredientFactory pizzaIngredientFactory;
    public ChicagoStylePizzaStore(PizzaIngredientFactory pizzaIngredientFactory){
        this.pizzaIngredientFactory = pizzaIngredientFactory;
    }
    @Override
   protected Pizza createPizza(PizzaType type) {
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
