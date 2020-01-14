package de.arthurpicht.cli.command;

import de.arthurpicht.cli.common.UnrecognizedArgumentException;

public class CommandSyntaxException extends UnrecognizedArgumentException {

    public CommandSyntaxException(String[] args, int argumentIndex, String message) {
        super(args, argumentIndex, message);
    }

}
