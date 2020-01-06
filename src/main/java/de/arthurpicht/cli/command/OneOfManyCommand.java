package de.arthurpicht.cli.command;

import de.arthurpicht.utils.core.strings.Strings;

import java.util.*;

public class OneOfManyCommand extends Command {

    private Set<String> commandStringSet;

    public OneOfManyCommand(Command previousCommand, String... commands) {
        super(previousCommand);

        // TODO assert
        if (commands.length == 0) throw new CommandSpecException("command list empty");

        this.commandStringSet = new HashSet<>(Arrays.asList(commands));
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public Set<String> getCommandStrings() {
        return this.commandStringSet;
    }

    @Override
    public boolean matches(String commandString) {
        return this.commandStringSet.contains(commandString);
    }

    @Override
    public String toString() {
        return Strings.listing(this.commandStringSet, " | ", "[ ", " ]");
    }
}
