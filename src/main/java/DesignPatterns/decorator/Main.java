package DesignPatterns.decorator;

import DesignPatterns.decorator.implClass.Espresso;
import DesignPatterns.decorator.implClass.decorators.Milk;
import DesignPatterns.decorator.implClass.decorators.Soy;

public class Main {
    public static void main(String []args){
        Beverage beverage = new Espresso();
        System.out.println("Description : " + beverage.getDescription()+"  cost : "+beverage.cost());

        beverage = new Milk(beverage);
        System.out.println("Description : " + beverage.getDescription()+"  cost : "+beverage.cost());

        beverage = new Soy(beverage);
        System.out.println("Description : " + beverage.getDescription()+"  cost : "+beverage.cost());

    }
}
