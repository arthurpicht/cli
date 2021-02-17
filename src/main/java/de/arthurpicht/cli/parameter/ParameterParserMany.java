package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CommandLineInterfaceResultBuilder;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Arguments;
import de.arthurpicht.cli.common.ParserResult;

public class ParameterParserMany extends ParameterParser {

    private final int nrOfArguments;

    public ParameterParserMany(int nrOfArguments, CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder) {
        super(commandLineInterfaceResultBuilder);
        this.nrOfArguments = nrOfArguments;
    }

    @Override
    public void parse(ArgumentIterator argumentIterator) throws ParameterParserException {

        Arguments arguments = argumentIterator.getArguments();
        int beginIndex = argumentIterator.getIndex() + 1;

        if (arguments.size() < this.nrOfArguments + beginIndex)
            throw new IllegalNumberOfParametersException(arguments, arguments.size(), this.nrOfArguments, arguments.size() - beginIndex);

        while (argumentIterator.hasNext()) {
            String argument = argumentIterator.getNext();
            this.parameterList.add(argument);
        }

        checkForTooManyParameters(arguments, beginIndex);

        this.commandLineInterfaceResultBuilder.withParameterParserResult(getParserResult());
    }

    private void checkForTooManyParameters(Arguments args, int beginIndex) throws IllegalNumberOfParametersException {
        if (args.size() > beginIndex + this.nrOfArguments) {
            int indexFailPointer = beginIndex + this.nrOfArguments;
            int nrParametersFound = args.size() - beginIndex;
            throw new IllegalNumberOfParametersException(args, indexFailPointer, this.nrOfArguments, nrParametersFound);
        }
    }
}
