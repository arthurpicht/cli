package de.arthurpicht.cli.common;

public class CLIParserException extends Exception {

    public CLIParserException() {
    }

    public CLIParserException(String message) {
        super(message);
    }

    public CLIParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public CLIParserException(Throwable cause) {
        super(cause);
    }

    public CLIParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
