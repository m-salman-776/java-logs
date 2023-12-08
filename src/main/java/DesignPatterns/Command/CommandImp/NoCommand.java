package DesignPatterns.Command.CommandImp;

import DesignPatterns.Command.Command;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoCommand implements Command {
    @Override
    public void execute() {

    }
}
