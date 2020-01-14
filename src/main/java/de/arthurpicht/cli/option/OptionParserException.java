package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.UnrecognizedArgumentException;

public class OptionParserException extends UnrecognizedArgumentException {

    public OptionParserException(String[] args, int argumentIndex, String message) {
        super(args, argumentIndex, message);
    }

    //    public OptionParserException() {
//    }
//
//    public OptionParserException(String message) {
//        super(message);
//    }
//
//    public OptionParserException(String message, Throwable cause) {
//        super(message, cause);
//    }
//
//    public OptionParserException(Throwable cause) {
//        super(cause);
//    }
//
//    public OptionParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//        super(message, cause, enableSuppression, writableStackTrace);
//    }
}
