package DesignPatterns.Command.CommandImp;

import DesignPatterns.Command.Command;
import DesignPatterns.Command.VendorClasses.HallLight;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HallLightOff implements Command {
    HallLight hallLight;
    @Override
    public void execute() {
        hallLight.off();
    }
}
