package DesignPatterns.factory;

//import com.factory.ingredients.*;
import DesignPatterns.factory.ingredients.*;

import java.util.ArrayList;

public abstract class Pizza {
    protected String name;
    protected Dough dough;
    protected Sauce sauce;
    protected Pepperoni pepperoni;
    protected Cheese cheese;
    protected Clams clams;
    protected ArrayList<Veggies> veggies;
    protected ArrayList<String> toppings = new ArrayList<>(); // TODO DI violation
    protected abstract void prepare() ;
    //{
//        System.out.println("Preparing "+name);
//        System.out.println("Tossing Dough...");
//        System.out.println("Adding Sauce.....");
//        System.out.println("Adding Toppings....");
//        for (String topping : toppings){
//            System.out.println(" " + topping);
//        }
   // }

    public   void bake() {
        System.out.println("Bake for 25 min at 350 deg");
    }

    public void cut() {
        System.out.println("Cutting the pizza into Diagonal Slices");
    }

    public void box() {
        System.out.println("Putting pizza in box");
    }
}
