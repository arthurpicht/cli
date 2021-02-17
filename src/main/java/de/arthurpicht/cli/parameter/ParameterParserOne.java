package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CommandLineInterfaceResultBuilder;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Arguments;

/**
 * Expects exactly one argument.
 */
public class ParameterParserOne extends ParameterParser {

    public ParameterParserOne(CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder) {
        super(commandLineInterfaceResultBuilder);
    }

    @Override
    public void parse(ArgumentIterator argumentIterator) throws ParameterParserException {

        Arguments arguments = argumentIterator.getArguments();;

        if (argumentIterator.hasNext()) {
            String argument = argumentIterator.getNext();
            this.parameterList.add(argument);
        } else {
            throw new ParameterParserException(arguments, argumentIterator.getIndex() + 1, "One parameter expected.");
        }

        this.commandLineInterfaceResultBuilder.withParameterParserResult(getParserResult());
    }

}
