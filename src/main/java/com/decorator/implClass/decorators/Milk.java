package com.decorator.implClass.decorators;

import com.decorator.Beverage;
import com.decorator.implClass.CondimentDecorator;

public class Milk extends CondimentDecorator {
//    Beverage beverage;
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
