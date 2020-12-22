package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Arguments;

public class ValueExpectedException extends OptionParserException {

    public ValueExpectedException(ArgumentIterator argumentIterator) {
        super(
                argumentIterator.getArguments(),
                argumentIterator.getIndex() + 1,
                "Value expected for option: '" + argumentIterator.getCurrent() + "'."
        );
    }

    public static ValueExpectedException forPreviousArgument(ArgumentIterator argumentIterator) {

        if (!argumentIterator.hasPrevious()) {
            throw new IllegalStateException("No previous argument.");
        }

        int index = argumentIterator.getIndex();
        String argument = argumentIterator.getArguments().get(index - 1);

        return new ValueExpectedException(
                argumentIterator.getArguments(),
                argumentIterator.getIndex(),
                "Value expected for option '" + argument + "'."
        );

    }

    public ValueExpectedException(Arguments arguments, int argumentIndex, String message) {
        super(arguments, argumentIndex, message);
    }

}
