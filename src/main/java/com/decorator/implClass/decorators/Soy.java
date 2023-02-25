package com.decorator.implClass.decorators;

import com.decorator.Beverage;
import com.decorator.implClass.CondimentDecorator;

public class Soy extends CondimentDecorator {
//    Beverage beverage;
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
