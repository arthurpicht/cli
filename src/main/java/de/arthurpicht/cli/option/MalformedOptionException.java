package de.arthurpicht.cli.option;

public class MalformedOptionException extends OptionParserException {

    public MalformedOptionException(String[] args, int argumentIndex, String message) {
        super(args, argumentIndex, message);
    }

}
