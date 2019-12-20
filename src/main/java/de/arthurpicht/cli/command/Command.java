package de.arthurpicht.cli.command;

import java.util.HashSet;
import java.util.Set;

public abstract class Command {

    private Command previousCommand;
    private Set<Command> nextCommands;

    public abstract boolean isOpen();

    public abstract Set<String> getCommands();

    public Command(Command previousCommand) {
        this.previousCommand = previousCommand;
        this.nextCommands = new HashSet<>();
    }

//    public void setPrevious(Command command) {
//        this.previousCommand = command;
//    }

    public boolean hasPrevious() {
        return (this.previousCommand != null);
    }

    public Command getPrevious() {
        return this.previousCommand;
    }

    public void addNext(Command command) {
        this.nextCommands.add(command);
    }

    public Set<Command> getNext() {
        return this.nextCommands;
    }

}
