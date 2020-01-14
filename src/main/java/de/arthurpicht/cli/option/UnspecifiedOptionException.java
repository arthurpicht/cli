package de.arthurpicht.cli.option;

public class UnspecifiedOptionException extends OptionParserException {

    public UnspecifiedOptionException(String[] args, int argumentIndex) {
        super(args, argumentIndex, "Unknown option: '" + args[argumentIndex] + "'.");
    }

    public UnspecifiedOptionException(String[] args, int argumentIndex, String message) {
        super(args, argumentIndex, message);
    }


}
