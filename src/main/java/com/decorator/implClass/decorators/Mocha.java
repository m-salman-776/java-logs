package com.decorator.implClass.decorators;

import com.decorator.Beverage;
import com.decorator.implClass.CondimentDecorator;

public class Mocha extends CondimentDecorator {
//    Beverage beverage;
    public Mocha(Beverage beverage){
        this.beverage = beverage;
    }
    @Override
    public double cost() {
        return beverage.cost() + 2.5;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() +  ", Mocha ";
    }
}
