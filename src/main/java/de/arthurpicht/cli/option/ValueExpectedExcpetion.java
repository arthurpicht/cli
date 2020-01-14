package de.arthurpicht.cli.option;

public class ValueExpectedExcpetion extends OptionParserException {


    public ValueExpectedExcpetion(String[] args, int argumentIndex, String message) {
        super(args, argumentIndex, message);
    }


}
