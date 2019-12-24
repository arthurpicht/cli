package de.arthurpicht.cli.command;

import de.arthurpicht.utils.core.strings.Strings;

import java.util.*;

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

    protected String getCommandChainString() {

        List<String> commandChainList = new ArrayList<>();
        commandChainList.add(this.toString());
        Command command = this;
        while(command.hasPrevious()) {
            command = command.getPrevious();
            commandChainList.add(command.toString());
        }

        Collections.reverse(commandChainList);

        return Strings.listing(commandChainList, " ");

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return this.getCommandChainString().equals(command.getCommandChainString());
    }

    @Override
    public int hashCode() {
        return getCommandChainString().hashCode();
    }
}


