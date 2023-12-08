package DesignPatterns.decorator;

public abstract class Beverage {
    protected String description = "Unknown";
    public String getDescription(){
        return description;
    }
    public abstract double cost();
}
