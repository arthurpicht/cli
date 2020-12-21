package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.ArgumentIterator;

/**
 * Thrown in processing stage when an option was found that was not specified.
 */
public class UnspecifiedOptionException extends OptionParserException {

    public UnspecifiedOptionException(ArgumentIterator argumentIterator) {
        super(argumentIterator, "Unknown option: '" + argumentIterator.getCurrent() + "'");
    }



//    public UnspecifiedOptionException(String[] args, int argumentIndex) {
//        super(args, argumentIndex, "Unknown option: '" + args[argumentIndex] + "'");
//    }
//
//    public UnspecifiedOptionException(String[] args, int argumentIndex, String message) {
//        super(args, argumentIndex, message);
//    }


}
