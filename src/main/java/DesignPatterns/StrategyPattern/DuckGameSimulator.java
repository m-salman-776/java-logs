package DesignPatterns.StrategyPattern;

import DesignPatterns.StrategyPattern.DuckImpl.MallardDuck;
import DesignPatterns.StrategyPattern.DuckImpl.ReadHeadDuck;

public class DuckGameSimulator {
    public static void main(String []args){
        Duck mallard = new MallardDuck();
        Duck readheadDuck = new ReadHeadDuck();
        readheadDuck.performFly();
        mallard.performFly();
    }
}
