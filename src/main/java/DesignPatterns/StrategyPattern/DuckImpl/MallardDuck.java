package DesignPatterns.StrategyPattern.DuckImpl;

import DesignPatterns.StrategyPattern.Duck;
import DesignPatterns.StrategyPattern.FlyImpl.FlyWithRocket;
import DesignPatterns.StrategyPattern.QuackImpl.Squeak;

public class MallardDuck extends Duck {
    public MallardDuck() {
        super(new FlyWithRocket(), new Squeak());
    }

    @Override
    public void display() {
        System.out.println("I'm mallard Duck");
    }
}
