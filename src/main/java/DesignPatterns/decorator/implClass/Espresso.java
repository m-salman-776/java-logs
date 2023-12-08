package DesignPatterns.decorator.implClass;

import DesignPatterns.decorator.Beverage;

public class Espresso extends Beverage {
    public Espresso(){
        description = "Espresso";
    }
    @Override
    public double cost() {
        return 3.0;
    }
}
