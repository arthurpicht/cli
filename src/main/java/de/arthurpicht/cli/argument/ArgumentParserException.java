package de.arthurpicht.cli.argument;

import de.arthurpicht.cli.common.CLIParserException;

public class ArgumentParserException extends CLIParserException {

    public ArgumentParserException() {
    }

    public ArgumentParserException(String message) {
        super(message);
    }

    public ArgumentParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentParserException(Throwable cause) {
        super(cause);
    }

    public ArgumentParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
