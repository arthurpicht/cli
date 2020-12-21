package de.arthurpicht.cli.command.exceptions;

import de.arthurpicht.cli.command.Command;
import de.arthurpicht.cli.command.CommandsHelper;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Arguments;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;

import java.util.Set;

public class InsufficientNrOfCommandsException extends CommandParserException {

    private final Set<Command> validCommandSet;

    public static InsufficientNrOfCommandsException createInstance(ArgumentIterator argumentIterator, Set<Command> validCommandSet) {
        String message = "Insufficient number of commands. Next command is one of: " + CommandsHelper.toFormattedList(validCommandSet) + ".";
        return new InsufficientNrOfCommandsException(
                argumentIterator.getArguments(),
                argumentIterator.getIndex() + 1,
                message, validCommandSet);
    }

    private InsufficientNrOfCommandsException(Arguments arguments, int argumentIndex, String message, Set<Command> validCommandSet) {
        super(arguments, argumentIndex, message);
        this.validCommandSet = validCommandSet;
    }

    public Set<Command> getValidCommandSet() {
        return this.validCommandSet;
    }

}
