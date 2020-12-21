package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Arguments;

/**
 * Thrown in processing stage when an option is syntactically incorrect.
 */
public class MalformedOptionException extends OptionParserException {

    public MalformedOptionException(ArgumentIterator argumentIterator) {
        super(argumentIterator.getArguments(), argumentIterator.getIndex(), "Illegal option format: '" + argumentIterator.getCurrent() + "'");
    }

    public MalformedOptionException(Arguments arguments, int argumentIndex) {
        super(arguments, argumentIndex, "Illegal option format: '" + arguments.get(argumentIndex) + "'");
    }

    public MalformedOptionException(Arguments arguments, int argumentIndex, String message) {
        super(arguments, argumentIndex, message);
    }

}
