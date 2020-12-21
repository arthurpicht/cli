package de.arthurpicht.cli.command.exceptions;

import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Arguments;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;

public class CommandParserException extends UnrecognizedArgumentException {

    public CommandParserException(ArgumentIterator argumentIterator, String message) {
        super(argumentIterator, message);
    }

    public CommandParserException(Arguments arguments, int argumentIndex, String message) {
        super(arguments, argumentIndex, message);
    }
}
