package de.arthurpicht.cli.option;

/**
 * Thrown in processing stage when an option was found that was not specified.
 */
public class UnspecifiedOptionException extends OptionParserException {

    public UnspecifiedOptionException(String[] args, int argumentIndex) {
        super(args, argumentIndex, "Unknown option: '" + args[argumentIndex] + "'");
    }

    public UnspecifiedOptionException(String[] args, int argumentIndex, String message) {
        super(args, argumentIndex, message);
    }


}
