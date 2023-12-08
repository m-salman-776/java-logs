package DesignPatterns.decorator.implClass;

import DesignPatterns.decorator.Beverage;

public class Decaf extends Beverage {
    public Decaf(){
        description = "Decaf";
    }
    @Override
    public double cost() {
        return 2.0;
    }
}
