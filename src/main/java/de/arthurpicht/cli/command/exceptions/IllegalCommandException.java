package de.arthurpicht.cli.command.exceptions;

import de.arthurpicht.cli.command.Command;
import de.arthurpicht.cli.command.CommandsHelper;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;

import java.util.Set;

public class IllegalCommandException extends CommandParserException {

    private final Set<Command> validCommandSet;

    public static IllegalCommandException createInstance(ArgumentIterator argumentIterator, Set<Command> validCommandSet) {
        String message = "Command [" + argumentIterator.getCurrent() + "] not recognized. Possible commands are: " + CommandsHelper.toFormattedList(validCommandSet) + ".";
        return new IllegalCommandException(argumentIterator, message, validCommandSet);
    }

    public IllegalCommandException(ArgumentIterator argumentIterator, String message, Set<Command> validCommandSet) {
        super(argumentIterator, message);
        this.validCommandSet = validCommandSet;
    }

    public Set<Command> getValidCommandSet() {
        return this.validCommandSet;
    }

}
