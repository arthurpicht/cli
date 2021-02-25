package de.arthurpicht.cli.command.exceptions;

import de.arthurpicht.cli.command.CommandsHelper;
import de.arthurpicht.cli.command.tree.Command;
import de.arthurpicht.cli.common.ArgumentIterator;

import java.util.Set;

public class IllegalCommandException extends CommandParserException {

    public static IllegalCommandException createInstance(String executableName, ArgumentIterator argumentIterator, Set<Command> validCommandSet) {
        String message = "Command [" + argumentIterator.getCurrent() + "] not recognized. Possible commands are: "
                + CommandsHelper.toFormattedList(validCommandSet) + ".";
        return new IllegalCommandException(executableName, argumentIterator, message);
    }

    public static IllegalCommandException createInstanceForDoubleDash(String executableName, ArgumentIterator argumentIterator, Set<Command> validCommandSet) {
        String message = "Double dash \"--\" not allowed here. Possible commands are: "
                + CommandsHelper.toFormattedList(validCommandSet) + ".";
        return new IllegalCommandException(executableName, argumentIterator, message);
    }

    public IllegalCommandException(String executableName, ArgumentIterator argumentIterator, String message) {
        super(executableName, argumentIterator, message);
    }

}
