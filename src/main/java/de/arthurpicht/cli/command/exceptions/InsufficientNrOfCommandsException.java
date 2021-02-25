package de.arthurpicht.cli.command.exceptions;

import de.arthurpicht.cli.command.CommandsHelper;
import de.arthurpicht.cli.command.tree.Command;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Arguments;

import java.util.Set;

public class InsufficientNrOfCommandsException extends CommandParserException {

    public static InsufficientNrOfCommandsException createInstance(String execName, ArgumentIterator argumentIterator, Set<Command> validCommandSet) {

        String message = "Insufficient number of commands. Next command is one of: "
                + CommandsHelper.toFormattedList(validCommandSet) + ".";

        return new InsufficientNrOfCommandsException(
                execName,
                argumentIterator.getArguments(),
                argumentIterator.getIndex() + 1,
                message);
    }

    public static InsufficientNrOfCommandsException createInstanceWithNoCommandAsOption(String execName, ArgumentIterator argumentIterator, Set<Command> validCommandSet) {

        String message = "Insufficient number of commands. Next command is one of: "
                + CommandsHelper.toFormattedList(validCommandSet) + " or no command.";

        return new InsufficientNrOfCommandsException(
                execName,
                argumentIterator.getArguments(),
                argumentIterator.getIndex() + 1,
                message);
    }

    private InsufficientNrOfCommandsException(String execName, Arguments arguments, int argumentIndex, String message) {
        super(execName, arguments, argumentIndex, message);
    }

}
