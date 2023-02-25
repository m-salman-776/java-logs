package com.decorator.implClass;

import com.decorator.Beverage;

public class HouseBlend extends Beverage {
    public HouseBlend(){
        description = "House Blend";
    }
    @Override
    public double cost() {
        return 4.0;
    }
}
