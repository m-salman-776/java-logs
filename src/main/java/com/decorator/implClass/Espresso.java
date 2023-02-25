package com.decorator.implClass;

import com.decorator.Beverage;

public class Espresso extends Beverage {
    public Espresso(){
        description = "Espresso";
    }
    @Override
    public double cost() {
        return 3.0;
    }
}
