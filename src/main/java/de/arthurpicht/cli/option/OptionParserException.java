package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Arguments;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;

public class OptionParserException extends UnrecognizedArgumentException {

    public OptionParserException(ArgumentIterator argumentIterator, String message) {
        super(argumentIterator.getArguments(), argumentIterator.getIndex(), message);
    }

    public OptionParserException(Arguments arguments, int argumentIndex, String message) {
        super(arguments, argumentIndex, message);
    }

}
