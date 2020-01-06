package de.arthurpicht.cli.command;

public class CommandSyntaxError extends Exception {

    private Command command;

    public CommandSyntaxError(String message) {
        super(message);
    }

    public CommandSyntaxError(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandSyntaxError(Throwable cause) {
        super(cause);
    }

    public CommandSyntaxError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
