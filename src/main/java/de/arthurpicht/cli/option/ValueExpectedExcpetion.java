package de.arthurpicht.cli.option;

public class ValueExpectedExcpetion extends OptionParserException {

    public ValueExpectedExcpetion(String[] args, int argumentIndex) {
        super(args, argumentIndex, "Value expected for option: '" + args[argumentIndex - 1] + "'");
    }

    public ValueExpectedExcpetion(String[] args, int argumentIndex, String message) {
        super(args, argumentIndex, message);
    }


}
