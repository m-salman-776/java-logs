package com.factory.PizzaStoreImpl;

import com.factory.Pizza;
import com.factory.PizzaImpl.NYStyleCheesePizza;
import com.factory.PizzaImpl.NYStyleClamPizza;
import com.factory.PizzaImpl.NYStyleVeggiePizza;
import com.factory.PizzaStore;
import com.factory.PizzaType;

public class NYStylePizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza(PizzaType type) {
        if (type == PizzaType.CHEESE){
            return new NYStyleCheesePizza();
        }else if (type == PizzaType.CLAM){ //
            return new NYStyleClamPizza();
        }else {
            return new NYStyleVeggiePizza();
        }
    }
}
