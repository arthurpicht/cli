package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CliResultBuilder;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Arguments;

/**
 * Expects exactly one argument.
 */
public class ParameterParserOne extends ParameterParser {

    private final String executableName;

    public ParameterParserOne(CliResultBuilder cliResultBuilder, String executableName) {
        super(cliResultBuilder);
        this.executableName = executableName;
    }

    @Override
    public void parse(ArgumentIterator argumentIterator) throws ParameterParserException {

        Arguments arguments = argumentIterator.getArguments();

        if (argumentIterator.hasNext()) {
            String argument = argumentIterator.getNext();
            this.parameterList.add(argument);
        } else {
            throw new ParameterParserException(this.executableName, arguments, argumentIterator.getIndex() + 1,
                    "One parameter expected.");
        }

        this.cliResultBuilder.withParameterParserResult(getParserResult());
    }

}
