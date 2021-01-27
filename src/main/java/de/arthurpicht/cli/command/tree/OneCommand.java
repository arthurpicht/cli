package de.arthurpicht.cli.command.tree;

import de.arthurpicht.cli.command.exceptions.CommandSpecException;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.HashSet;
import java.util.Set;

public class OneCommand extends Command {

    private final String commandString;

    public OneCommand(String commandString) {
        super();
        if (Strings.isNullOrEmpty(commandString)) throw new CommandSpecException("Command string is null or empty.");
        this.commandString = commandString;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public String getCommandString() {
        return this.commandString;
    }

    @Override
    public boolean matches(String commandString) {
        return this.commandString.equals(commandString);
    }

    @Override
    public Set<String> getMatchingCandidates(String commandString) {
        Set<String> matchingCandidates = new HashSet<>();

        if (this.commandString.startsWith(commandString)) {
            matchingCandidates.add(this.commandString);
        }

        return matchingCandidates;
    }

    @Override
    public String asString() {
        return this.commandString;
    }

    public String toString() {
        return this.commandString;
    }

}
