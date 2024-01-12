package LLD.SolidPrinciples;

public class SolidPrinciples {
}

class Animal {
    String species;
    public void print(String str){
        System.out.println(str);
    }
}
// TODO Single Responsibility : each class should have well define responsibility and only one reason to change
// TODO bad design
class BadClassForBird extends Animal{
    public void fly(){
        if (species == "DODO"){
            print("DODO");
        }else if (species == "PENGUIN"){
            print("PENGUIN");
        }else if (species == "ELDOrado"){
            print("Eldorado");
        }
        // if some more species added we need to make changes here in this class breaking single responsibility;
    }
}
abstract class Bird extends Animal{
    public abstract void fly() ;
}

//class




