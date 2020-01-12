package de.arthurpicht.cli.argument;

import de.arthurpicht.cli.common.UnrecognizedCLArgumentException;

public class ArgumentParserException extends UnrecognizedCLArgumentException {

    public ArgumentParserException() {
    }

    public ArgumentParserException(String[] args, int argumentIndex, String message) {
        super(args, argumentIndex, message);
    }

//    public ArgumentParserException(String message, Throwable cause) {
//        super(message, cause);
//    }
//
//    public ArgumentParserException(Throwable cause) {
//        super(cause);
//    }
//
//    public ArgumentParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//        super(message, cause, enableSuppression, writableStackTrace);
//    }
}
