package DesignPatterns.factory.PizzaStoreImpl;

import DesignPatterns.factory.Pizza;
import DesignPatterns.factory.PizzaImpl.NYStyleCheesePizza;
import DesignPatterns.factory.PizzaImpl.NYStyleClamPizza;
import DesignPatterns.factory.PizzaImpl.NYStyleVeggiePizza;
import DesignPatterns.factory.PizzaStore;
import DesignPatterns.factory.PizzaType;

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
