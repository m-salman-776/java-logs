package DesignPatterns.StrategyPattern.FlyImpl;

import DesignPatterns.StrategyPattern.Interface.FlyBehaviour;

public class FlyWithWings implements FlyBehaviour {
    @Override
    public void fly() {
        System.out.println("I'm flying with wings");
    }

}
