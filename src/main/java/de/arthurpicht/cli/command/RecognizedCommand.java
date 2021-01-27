package de.arthurpicht.cli.command;

import de.arthurpicht.cli.command.tree.Command;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RecognizedCommand {

    private final Command command;
    private final String commandName;
    private final String arg;

    public RecognizedCommand(Command command, String commandName, String arg) {
        this.command = command;
        this.commandName = commandName;
        this.arg = arg;
    }

    public Command getCommand() {
        return command;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArg() {
        return arg;
    }

    public static Set<String> getCommandNames(Set<RecognizedCommand> recognizedCommandSet) {
        Set<String> commandNameSet = new HashSet<>();
        for (RecognizedCommand recognizedCommand : recognizedCommandSet) {
            commandNameSet.add(recognizedCommand.getCommandName());
        }
        return commandNameSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecognizedCommand that = (RecognizedCommand) o;
        return command.equals(that.command) &&
                commandName.equals(that.commandName) &&
                arg.equals(that.arg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, commandName, arg);
    }
}
