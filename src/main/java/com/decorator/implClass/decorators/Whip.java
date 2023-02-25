package com.decorator.implClass.decorators;

import com.decorator.Beverage;
import com.decorator.implClass.CondimentDecorator;

public class Whip extends CondimentDecorator {
//    Beverage beverage ;
    public Whip(Beverage beverage){
        this.beverage = beverage;
    }
    @Override
    public double cost() {
        return beverage.cost() +  4.6;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() +  " , Whip ";
    }
}
