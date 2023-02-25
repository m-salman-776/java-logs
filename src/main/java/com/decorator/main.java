package com.decorator;

import com.decorator.implClass.Espresso;
import com.decorator.implClass.decorators.Milk;
import com.decorator.implClass.decorators.Soy;

public class main {
    public static void main(String args[]){
        Beverage beverage = new Espresso();
        System.out.println("Description : " + beverage.getDescription()+"  cost : "+beverage.cost());

        beverage = new Milk(beverage);
        System.out.println("Description : " + beverage.getDescription()+"  cost : "+beverage.cost());

        beverage = new Soy(beverage);
        System.out.println("Description : " + beverage.getDescription()+"  cost : "+beverage.cost());

    }
}
