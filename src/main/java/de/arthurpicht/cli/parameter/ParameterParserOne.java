package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Arguments;

/**
 * Expects exactly one argument.
 */
public class ParameterParserOne extends ParameterParser {

    @Override
    public void parse(ArgumentIterator argumentIterator) throws ParameterParserException {

        Arguments arguments = argumentIterator.getArguments();;

        if (argumentIterator.hasNext()) {
            String argument = argumentIterator.getNext();
            this.parameterList.add(argument);
        } else {
            throw new ParameterParserException(arguments, argumentIterator.getIndex() + 1, "One parameter expected.");
        }

//        if (arguments.size() > beginIndex) {
//            String argument = arguments.get(beginIndex);
//            this.parameterList.add(argument);
//            this.lastProcessedIndex = beginIndex;
//        } else {
//            throw new ParameterParserException(args, beginIndex, "One parameter expected.");
//        }
    }

}
