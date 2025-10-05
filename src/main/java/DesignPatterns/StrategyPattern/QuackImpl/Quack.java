package DesignPatterns.StrategyPattern.QuackImpl;

import DesignPatterns.StrategyPattern.Interface.QuackBehaviour;

public class Quack implements QuackBehaviour {
    @Override
    public void quack() {
        System.out.println("I'm doing quack");
    }
}
