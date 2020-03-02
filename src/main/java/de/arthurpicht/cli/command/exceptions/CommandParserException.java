package de.arthurpicht.cli.command.exceptions;

import de.arthurpicht.cli.common.UnrecognizedArgumentException;

public class CommandParserException extends UnrecognizedArgumentException {

    public CommandParserException(String[] args, int argumentIndex, String message) {
        super(args, argumentIndex, message);
    }
}
