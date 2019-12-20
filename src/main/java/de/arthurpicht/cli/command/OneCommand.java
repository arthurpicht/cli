package de.arthurpicht.cli.command;

import java.util.*;

public class OneCommand extends Command {

    private String command;

    public OneCommand(Command previousCommand, String command) {
        super(previousCommand);

        // TODO assert
        if (command == null || command.equals("")) throw new CommandSpecException("command is null or empty");

        this.command = command;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public HashSet<String> getCommands() {
        return new HashSet<>(Collections.singletonList(this.command));
    }

    @Override
    public String toString() {
        return "[ " + this.command + " ]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OneCommand that = (OneCommand) o;
        return command.equals(that.command);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command);
    }
}
