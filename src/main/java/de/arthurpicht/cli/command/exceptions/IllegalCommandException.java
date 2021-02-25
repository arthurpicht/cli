package de.arthurpicht.cli.command.exceptions;

import de.arthurpicht.cli.command.CommandsHelper;
import de.arthurpicht.cli.command.tree.Command;
import de.arthurpicht.cli.common.ArgumentIterator;

import java.util.Set;

public class IllegalCommandException extends CommandParserException {

    public static IllegalCommandException createInstance(String execName, ArgumentIterator argumentIterator, Set<Command> validCommandSet) {
        String message = "Command [" + argumentIterator.getCurrent() + "] not recognized. Possible commands are: "
                + CommandsHelper.toFormattedList(validCommandSet) + ".";
        return new IllegalCommandException(execName, argumentIterator, message);
    }

    public static IllegalCommandException createInstanceForDoubleDash(String execName, ArgumentIterator argumentIterator, Set<Command> validCommandSet) {
        String message = "Double dash \"--\" not allowed here. Possible commands are: "
                + CommandsHelper.toFormattedList(validCommandSet) + ".";
        return new IllegalCommandException(execName, argumentIterator, message);
    }

    public IllegalCommandException(String execName, ArgumentIterator argumentIterator, String message) {
        super(execName, argumentIterator, message);
    }

}
