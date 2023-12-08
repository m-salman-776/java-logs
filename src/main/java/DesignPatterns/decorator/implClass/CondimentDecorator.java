package DesignPatterns.decorator.implClass;

import DesignPatterns.decorator.Beverage;

public abstract class CondimentDecorator extends Beverage {
    // TODO need to check of we put it here instead of each extending class
//    protected Beverage beverage;
    public abstract String getDescription();
}
