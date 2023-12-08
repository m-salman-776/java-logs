package DesignPatterns.decorator.implClass;

import DesignPatterns.decorator.Beverage;

public class HouseBlend extends Beverage {
    public HouseBlend(){
        description = "House Blend";
    }
    @Override
    public double cost() {
        return 4.0;
    }
}
