package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Arguments;

public class ValueExpectedException extends OptionParserException {

    public ValueExpectedException(String executableName, ArgumentIterator argumentIterator) {
        super(
                executableName,
                argumentIterator.getArguments(),
                argumentIterator.getIndex() + 1,
                "Value expected for option: '" + argumentIterator.getCurrent() + "'."
        );
    }

    public static ValueExpectedException forPreviousArgument(String executableName, ArgumentIterator argumentIterator) {

        if (!argumentIterator.hasPrevious()) {
            throw new IllegalStateException("No previous argument.");
        }

        int index = argumentIterator.getIndex();
        String argument = argumentIterator.getArguments().get(index - 1);

        return new ValueExpectedException(
                executableName,
                argumentIterator.getArguments(),
                argumentIterator.getIndex(),
                "Value expected for option '" + argument + "'."
        );

    }

    public ValueExpectedException(String executableName, Arguments arguments, int argumentIndex, String message) {
        super(executableName, arguments, argumentIndex, message);
    }

}
