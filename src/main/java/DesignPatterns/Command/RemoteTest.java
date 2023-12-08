package DesignPatterns.Command;

import DesignPatterns.Command.CommandImp.*;
import DesignPatterns.Command.VendorClasses.Door;
import DesignPatterns.Command.VendorClasses.HallLight;

public class RemoteTest {
    public static void main(String []args){
        RemoteControl remoteControl = new RemoteControl(5);

        // Appliance to command conversion
        HallLightOn hallLightOn = new HallLightOn(new HallLight());
        HallLightOff hallLightOff = new HallLightOff(new HallLight());

        DoorOpen doorOpen = new DoorOpen(new Door());
        DoorClose doorClose = new DoorClose(new Door());


        Command [] partyOn = {hallLightOn,doorOpen};
        Command [] partyOff = {hallLightOff,doorClose};
        MacroCommand partyOnCommand = new MacroCommand(partyOn);
        MacroCommand partyOffCommand = new MacroCommand(partyOff);


        remoteControl.setCommand(1,hallLightOn,hallLightOff);
        remoteControl.setCommand(2,doorOpen,doorClose);

        remoteControl.setCommand(3,partyOnCommand,partyOffCommand);

        remoteControl.onButtonWasPushed(1);

        remoteControl.onButtonWasPushed(2);
        remoteControl.offButtonWasPushed(2);


    }
}
