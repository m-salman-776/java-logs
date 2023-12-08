package DesignPatterns.Command.CommandImp;

import DesignPatterns.Command.Command;
import DesignPatterns.Command.VendorClasses.Door;

public class DoorClose implements Command {
     Door door ;
    public DoorClose(Door door){
        this.door = door;
    }
     @Override
    public void execute(){
        this.door.close();
    }
}
