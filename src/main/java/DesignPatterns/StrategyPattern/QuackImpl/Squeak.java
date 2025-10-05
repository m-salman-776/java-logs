package DesignPatterns.StrategyPattern.QuackImpl;

import DesignPatterns.StrategyPattern.Interface.QuackBehaviour;

public class Squeak implements QuackBehaviour {
    @Override
    public void quack() {
        System.out.println("I'm squeaking");
    }
}
