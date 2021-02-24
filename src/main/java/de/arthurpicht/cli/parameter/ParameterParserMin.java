package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CliResultBuilder;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Arguments;

public class ParameterParserMin extends ParameterParser {

    private final int minimalNrOfArgument;

    public ParameterParserMin(int minimalNrOfArguments, CliResultBuilder cliResultBuilder) {
        super(cliResultBuilder);
        this.minimalNrOfArgument = minimalNrOfArguments;
    }

    @Override
    public void parse(ArgumentIterator argumentIterator) throws ParameterParserException {

        Arguments arguments = argumentIterator.getArguments();
        int beginIndex = argumentIterator.getIndex() + 1;

        if (arguments.size() < this.minimalNrOfArgument + beginIndex) {
            int nrFoundArgs = arguments.size() - beginIndex;
            throw new ParameterParserException(arguments, beginIndex + nrFoundArgs, "Wrong number of parameters. Minimal number expected: " + this.minimalNrOfArgument + ", found: " + nrFoundArgs + ".");
        }

        while (argumentIterator.hasNext()) {
            String argument = argumentIterator.getNext();
            this.parameterList.add(argument);
        }

        this.cliResultBuilder.withParameterParserResult(getParserResult());
    }

}
