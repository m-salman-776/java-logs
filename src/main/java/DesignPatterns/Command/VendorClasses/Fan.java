package DesignPatterns.Command.VendorClasses;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Fan {
    Speed speed;
    public void off (){
        this.speed = Speed.OFF;
    }
    public void slow(){
        this.speed = Speed.SLOW;
    }
    public void medium(){
        this.speed = Speed.MEDIUM;
    }
    public void fast(){
        this.speed = Speed.FAST;
    }
}

enum Speed {
    OFF,
    SLOW,
    MEDIUM,
    FAST
}