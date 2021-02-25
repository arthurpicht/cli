package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CliResultBuilder;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Arguments;

public class ParameterParserN extends ParameterParser {

    private final int nrOfArguments;
    private final String executableName;

    public ParameterParserN(int nrOfArguments, CliResultBuilder cliResultBuilder, String executableName) {
        super(cliResultBuilder);
        this.nrOfArguments = nrOfArguments;
        this.executableName = executableName;
    }

    @Override
    public void parse(ArgumentIterator argumentIterator) throws ParameterParserException {

        Arguments arguments = argumentIterator.getArguments();
        int beginIndex = argumentIterator.getIndex() + 1;

        if (arguments.size() < this.nrOfArguments + beginIndex)
            throw new IllegalNumberOfParametersException(this.executableName, arguments, arguments.size(), this.nrOfArguments, arguments.size() - beginIndex);

        while (argumentIterator.hasNext()) {
            String argument = argumentIterator.getNext();
            this.parameterList.add(argument);
        }

        checkForTooManyParameters(arguments, beginIndex);

        this.cliResultBuilder.withParameterParserResult(getParserResult());
    }

    private void checkForTooManyParameters(Arguments args, int beginIndex) throws IllegalNumberOfParametersException {
        if (args.size() > beginIndex + this.nrOfArguments) {
            int indexFailPointer = beginIndex + this.nrOfArguments;
            int nrParametersFound = args.size() - beginIndex;
            throw new IllegalNumberOfParametersException(this.executableName, args, indexFailPointer, this.nrOfArguments, nrParametersFound);
        }
    }
}
