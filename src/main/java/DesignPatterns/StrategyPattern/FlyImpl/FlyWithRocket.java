package DesignPatterns.StrategyPattern.FlyImpl;

import DesignPatterns.StrategyPattern.Interface.FlyBehaviour;

public class FlyWithRocket implements FlyBehaviour {
    @Override
    public void fly() {
        System.out.println("I'm flying with rocket");
    }
}
