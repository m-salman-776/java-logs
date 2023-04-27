package com.Command;

import com.Command.CommandImp.NoCommand;

public class RemoteControl {
    Command [] onCommand ;
    Command [] offCommand;
    public RemoteControl(int size){
        onCommand = new Command[size];
        offCommand = new Command[size];
        Command noCommand = new NoCommand();
        for (int i=0;i<size;i++){
            onCommand[i] = noCommand;
            offCommand[i] = noCommand;
        }
    }
    public void setCommand(int slot , Command onCommand, Command offCommand){
        this.offCommand[slot] = offCommand;
        this.onCommand[slot] = onCommand;
    }
    public void onButtonWasPushed(int slot){
        this.onCommand[slot].execute();
    }
    public void offButtonWasPushed(int slot){
        this.offCommand[slot].execute();;
    }
}
