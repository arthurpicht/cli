package de.arthurpicht.cli.command;

import de.arthurpicht.cli.command.exceptions.CommandSpecException;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.*;

public class OneCommand extends Command {

    private final String commandString;

    public OneCommand(Command previousCommand, String commandString) {
        super(previousCommand);
        if (Strings.isNullOrEmpty(commandString)) throw new CommandSpecException("Command string is null or empty.");
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
