package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Arguments;

public class ParameterParserVar extends ParameterParser {

    private final int minimalNrOfArgument;

    public ParameterParserVar(int minimalNrOfArguments) {
        this.minimalNrOfArgument = minimalNrOfArguments;
    }

    @Override
    public void parse(ArgumentIterator argumentIterator) throws ParameterParserException {

        Arguments arguments = argumentIterator.getArguments();
        int beginIndex = argumentIterator.getIndex() + 1;

        if (arguments.size() < this.minimalNrOfArgument + beginIndex) {
            int nrFoundArgs = arguments.size() - beginIndex;
            throw new ParameterParserException(arguments, beginIndex + nrFoundArgs, "Illegal number of arguments. Minimal number expected: " + this.minimalNrOfArgument + ", found: " + nrFoundArgs + ".");
        }

        while (argumentIterator.hasNext()) {
            String argument = argumentIterator.getNext();
            this.parameterList.add(argument);
        }

//        for(int i=beginIndex; i<args.length; i++) {
//            String argument = args[i];
//            this.parameterList.add(argument);
//        }
//
//        this.lastProcessedIndex = args.length - 1;

    }
}
