package com.Command.CommandImp;

import com.Command.Command;
import com.Command.VendorClasses.HallLight;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HallLightOn implements Command {
    HallLight hallLight;
    @Override
    public void execute() {
        hallLight.on();
    }
}

@AllArgsConstructor
class HallLightOff implements Command {
    HallLight hallLight;
    @Override
    public void execute() {
        hallLight.off();
    }
}


