package de.arthurpicht.cli.parameter;

/**
 * Expects exactly one argument.
 */
public class ParameterParserOne extends ParameterParser {

    @Override
    public void parse(String[] args, int beginIndex) throws ParameterParserException {

        if (args.length > beginIndex) {
            String argument = args[beginIndex];
            this.parameterList.add(argument);
            this.lastProcessedIndex = beginIndex;
        } else {
            throw new ParameterParserException(args, beginIndex, "One argument expected.");
        }
    }

}
