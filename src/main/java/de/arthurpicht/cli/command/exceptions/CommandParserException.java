package de.arthurpicht.cli.command.exceptions;

import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Arguments;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;

public class CommandParserException extends UnrecognizedArgumentException {

    public CommandParserException(String executableName, ArgumentIterator argumentIterator, String message) {
        super(executableName, argumentIterator, message);
    }

    public CommandParserException(String executableName, Arguments arguments, int argumentIndex, String message) {
        super(executableName, arguments, argumentIndex, message);
    }
}
