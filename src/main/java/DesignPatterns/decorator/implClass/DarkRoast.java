package DesignPatterns.decorator.implClass;

import DesignPatterns.decorator.Beverage;

public class DarkRoast extends Beverage {
    public DarkRoast(){
        description = "Dark Roast";
    }
    @Override
    public double cost() {
        return 1.0;
    }
}
