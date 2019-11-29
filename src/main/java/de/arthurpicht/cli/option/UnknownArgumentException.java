package de.arthurpicht.cli.option;

import de.arthurpicht.cli.option.OptionParserException;

public class UnknownArgumentException extends OptionParserException {

    private String argument;

    public UnknownArgumentException(String argument) {
        this.argument = argument;
    }

    public String getArgument() {
        return this.argument;
    }

}
