package DesignPatterns.decorator.implClass.decorators;

import DesignPatterns.decorator.Beverage;
import DesignPatterns.decorator.implClass.CondimentDecorator;

public class Soy extends CondimentDecorator {
    Beverage beverage;
    public Soy(Beverage beverage){
        this.beverage = beverage;
    }
    @Override
    public double cost() {
        return beverage.cost() + 3.5;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + " , Soy ";
    }
}
