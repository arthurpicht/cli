package de.arthurpicht.cli.command.exceptions;

import de.arthurpicht.cli.command.Command;
import de.arthurpicht.cli.command.CommandsHelper;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;

import java.util.Set;

public class InsufficientNrOfCommandsException extends CommandParserException {

    private Set<Command> validCommandSet;

    public static InsufficientNrOfCommandsException createInstance(String[] args, int argumentIndex, Set<Command> validCommandSet) {
        String message = "Insufficient number of commands. Next command is one of: " + CommandsHelper.toFormattedList(validCommandSet) + ".";
        return new InsufficientNrOfCommandsException(args, argumentIndex, message, validCommandSet);
    }

    public InsufficientNrOfCommandsException(String[] args, int argumentIndex, String message, Set<Command> validCommandSet) {
        super(args, argumentIndex, message);
        this.validCommandSet = validCommandSet;
    }

    public Set<Command> getValidCommandSet() {
        return this.validCommandSet;
    }

}
