package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.ArgumentIterator;

/**
 * Thrown in processing stage when an option is syntactically incorrect.
 */
public class MalformedOptionException extends OptionParserException {

    public MalformedOptionException(ArgumentIterator argumentIterator) {
        super(
                argumentIterator.getArguments(),
                argumentIterator.getIndex(),
                "Illegal option format: '" + argumentIterator.getCurrent() + "'");
    }

}
