package de.arthurpicht.cli.command.exceptions;

import de.arthurpicht.cli.command.Command;
import de.arthurpicht.cli.command.CommandsHelper;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;

import java.util.Set;

public class IllegalCommandException extends CommandParserException {

    private Set<Command> validCommandSet;

    public static IllegalCommandException createInstance(String[] args, int argumentIndex, Set<Command> validCommandSet) {
        String message = "No definition found for '" + args[argumentIndex] + "'. Possible commands are: " + CommandsHelper.toFormattedList(validCommandSet) + ".";
        return new IllegalCommandException(args, argumentIndex, message, validCommandSet);
    }

    public IllegalCommandException(String[] args, int argumentIndex, String message, Set<Command> validCommandSet) {
        super(args, argumentIndex, message);
        this.validCommandSet = validCommandSet;
    }

    public Set<Command> getValidCommandSet() {
        return this.validCommandSet;
    }

}
