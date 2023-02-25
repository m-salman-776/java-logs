package com.decorator.implClass;

import com.decorator.Beverage;

public class Decaf extends Beverage {
    public Decaf(){
        description = "Decaf";
    }
    @Override
    public double cost() {
        return 2.0;
    }
}
