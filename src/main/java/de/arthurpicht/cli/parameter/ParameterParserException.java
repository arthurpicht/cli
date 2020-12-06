package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.common.UnrecognizedArgumentException;

public class ParameterParserException extends UnrecognizedArgumentException {

//    public ParameterParserException() {
//    }

    public ParameterParserException(String[] args, int argumentIndex, String message) {
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