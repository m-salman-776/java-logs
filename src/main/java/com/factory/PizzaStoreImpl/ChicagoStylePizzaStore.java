package com.factory.PizzaStoreImpl;

import com.factory.Pizza;
import com.factory.PizzaImpl.ChicagoStyleCheesePizza;
import com.factory.PizzaImpl.ChicagoStyleClamPizza;
import com.factory.PizzaStore;
import com.factory.PizzaType;

public class ChicagoStylePizzaStore extends PizzaStore {
    @Override
   protected Pizza createPizza(PizzaType type) {
        if (type == PizzaType.CHEESE){
            return new ChicagoStyleCheesePizza();
        }else {
            return new ChicagoStyleClamPizza();
        }
    }

    // Cheese
    // Veggie
    // Clam
    // Pepperoni
}
