package de.arthurpicht.cli.argument;

/**
 * Expects exactly one argument.
 */
public class ArgumentParserOne extends ArgumentParser {

    @Override
    public void parse(String[] args, int beginIndex) throws ArgumentParserException {

        if (args.length > beginIndex) {
            String argument = args[beginIndex];
            this.argumentList.add(argument);
            this.lastProcessedIndex = beginIndex;
        } else {
            throw new ArgumentParserException(args, beginIndex, "One argument expected.");
        }
    }

}
