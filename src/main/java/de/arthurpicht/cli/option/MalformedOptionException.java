package de.arthurpicht.cli.option;

/**
 * Thrown in processing stage when an option is syntactically incorrect.
 */
public class MalformedOptionException extends OptionParserException {

    public MalformedOptionException(String[] args, int argumentIndex) {
        super(args, argumentIndex, "Illegal option format: '" + args[argumentIndex] + "'");
    }

    public MalformedOptionException(String[] args, int argumentIndex, String message) {
        super(args, argumentIndex, message);
    }

}
