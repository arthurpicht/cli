package de.arthurpicht.cli.option;

public class IllegalArgException extends OptionParserException {

    private String argument;

    public IllegalArgException(String argument) {
        this.argument = argument;
    }

    public String getArgument() {
        return this.argument;
    }
}
