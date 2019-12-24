package de.arthurpicht.cli.command;

import de.arthurpicht.utils.core.strings.Strings;

import java.util.*;

public class OneOfManyCommand extends Command {

    private Set<String> commandSet;

    public OneOfManyCommand(Command previousCommand, String... commands) {
        super(previousCommand);

        // TODO assert
        if (commands.length == 0) throw new CommandSpecException("command list empty");

        this.commandSet = new HashSet<>(Arrays.asList(commands));
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public Set<String> getCommands() {
        return this.commandSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OneOfManyCommand that = (OneOfManyCommand) o;
        return commandSet.equals(that.commandSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandSet);
    }

    @Override
    public String toString() {
        return Strings.listing(this.commandSet, " | ", "[ ", " ]");
    }
}
