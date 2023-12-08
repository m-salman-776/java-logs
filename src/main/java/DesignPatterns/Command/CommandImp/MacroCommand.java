package DesignPatterns.Command.CommandImp;

import DesignPatterns.Command.Command;
import lombok.AllArgsConstructor;


public class MacroCommand implements Command{
    Command [] commands;

    public MacroCommand(Command [] commands){
        this.commands = commands;
    }
    @Override
    public void execute() {
        for (Command command : commands){
            command.execute();
        }
    }
}
