package DesignPatterns.StrategyPattern.DuckImpl;

import DesignPatterns.StrategyPattern.Duck;
import DesignPatterns.StrategyPattern.FlyImpl.FlyWithWings;
import DesignPatterns.StrategyPattern.QuackImpl.Squeak;

public class ReadHeadDuck extends Duck {
    public ReadHeadDuck() {
        super(new FlyWithWings(), new Squeak());
    }
    @Override
    public void display() {
        System.out.println("I'm ReadHead Duck");
    }
}
