package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Arguments;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;

public class OptionParserException extends UnrecognizedArgumentException {

    public OptionParserException(String executableName, ArgumentIterator argumentIterator, String message) {
        super(executableName, argumentIterator.getArguments(), argumentIterator.getIndex(), message);
    }

    public OptionParserException(String executableName, Arguments arguments, int argumentIndex, String message) {
        super(executableName, arguments, argumentIndex, message);
    }

}
