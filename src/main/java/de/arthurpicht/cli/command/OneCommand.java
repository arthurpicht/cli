package de.arthurpicht.cli.command;

import java.util.*;

public class OneCommand extends Command {

    private String commandString;

    public OneCommand(Command previousCommand, String commandString) {
        super(previousCommand);

        // TODO assert
        if (commandString == null || commandString.equals("")) throw new CommandSpecException("command is null or empty");

        this.commandString = commandString;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public HashSet<String> getCommandStrings() {
        return new HashSet<>(Collections.singletonList(this.commandString));
    }

    @Override
    public boolean matches(String commandString) {
        return this.commandString.equals(commandString);
    }

    @Override
    public Set<String> getAsStrings() {
        return new HashSet<>(Collections.singletonList(this.commandString));
    }

    @Override
    public String toString() {
        return "[ " + this.commandString + " ]";
    }

}
