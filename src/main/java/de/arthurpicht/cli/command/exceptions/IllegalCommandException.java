package de.arthurpicht.cli.command.exceptions;

import de.arthurpicht.cli.command.CommandsHelper;
import de.arthurpicht.cli.command.tree.Command;
import de.arthurpicht.cli.common.ArgumentIterator;

import java.util.Set;

public class IllegalCommandException extends CommandParserException {

    public static IllegalCommandException createInstance(ArgumentIterator argumentIterator, Set<Command> validCommandSet) {
        String message = "Command [" + argumentIterator.getCurrent() + "] not recognized. Possible commands are: "
                + CommandsHelper.toFormattedList(validCommandSet) + ".";
        return new IllegalCommandException(argumentIterator, message, validCommandSet);
    }

    public static IllegalCommandException createInstanceForDoubleDash(ArgumentIterator argumentIterator, Set<Command> validCommandSet) {
        String message = "Double dash \"--\" not allowed here. Possible commands are: "
                + CommandsHelper.toFormattedList(validCommandSet) + ".";
        return new IllegalCommandException(argumentIterator, message, validCommandSet);
    }

    public IllegalCommandException(ArgumentIterator argumentIterator, String message, Set<Command> validCommandSet) {
        super(argumentIterator, message);
    }

}
