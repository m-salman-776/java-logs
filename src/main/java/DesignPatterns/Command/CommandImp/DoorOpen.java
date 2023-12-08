package DesignPatterns.Command.CommandImp;

import DesignPatterns.Command.Command;
import DesignPatterns.Command.VendorClasses.Door;

public class DoorOpen implements Command {
    Door door;
    public DoorOpen(Door door){
        this.door = door;
    }
    @Override
    public void execute() {
        this.door.open();
    }
}
