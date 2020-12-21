package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Arguments;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;

public class OptionParserException extends UnrecognizedArgumentException {

    public OptionParserException(ArgumentIterator argumentIterator, String message) {
        super(argumentIterator.getArguments(), argumentIterator.getIndex(), message);
    }

    public OptionParserException(Arguments arguments, int argumentIndex, String message) {
        super(arguments, argumentIndex, message);
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
