package de.arthurpicht.cli.option;

public class ValueExpectedExcpetion extends OptionParserException {

    private String argument;

    public ValueExpectedExcpetion(String argument) {
        this.argument = argument;
    }

    public String getArgument() {
        return this.argument;
    }

}
