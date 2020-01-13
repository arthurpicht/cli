package de.arthurpicht.cli.command;

import de.arthurpicht.cli.common.UnrecognizedArgumentException;

public class CommandSyntaxError extends UnrecognizedArgumentException {

//    private Command command;

    public CommandSyntaxError(String message) {
        super(message);
    }

    public CommandSyntaxError(String[] args, int argumentIndex, String message) {
        super(args, argumentIndex, message);
    }

//    public CommandSyntaxError(String message, Throwable cause) {
//        super(message, cause);
//    }
//
//    public CommandSyntaxError(Throwable cause) {
//        super(cause);
//    }
//
//    public CommandSyntaxError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//        super(message, cause, enableSuppression, writableStackTrace);
//    }
}
