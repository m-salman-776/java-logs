package DesignPatterns.factory;

//import com.factory.ingredients.*;
import DesignPatterns.factory.ingredients.*;

public interface PizzaIngredientFactory {
    public Dough createDough();
    public Sauce createSauce();
    public Cheese createCheese();
    public Clams createClams();
    public Veggies[] createVeggies();
    public Pepperoni createPepperoni();
}
