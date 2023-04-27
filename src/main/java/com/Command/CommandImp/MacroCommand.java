package com.Command.CommandImp;

import com.Command.Command;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MacroCommand implements Command{
    Command [] commands;
    @Override
    public void execute() {
        for (Command command : commands){
            command.execute();
        }
    }
}
