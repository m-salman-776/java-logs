package DesignPatterns.decorator.implClass.decorators;

import DesignPatterns.decorator.Beverage;
import DesignPatterns.decorator.implClass.CondimentDecorator;

public class Milk extends CondimentDecorator {
    Beverage beverage;
    public Milk(Beverage beverage){
        this.beverage = beverage;
    }
    @Override
    public double cost() {
        return beverage.cost() + 1.5;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Milk";
    }
}
