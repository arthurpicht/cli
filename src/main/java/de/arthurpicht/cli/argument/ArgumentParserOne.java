package de.arthurpicht.cli.argument;

/**
 * Expects exactly one argument.
 */
public class ArgumentParserOne extends ArgumentParser {

    @Override
    public void parse(String[] args, int beginIndex) throws ArgumentParserException {

        this.isParsed = true;

        if (args.length > beginIndex) {
            String argument = args[beginIndex];
            this.argumentList.add(argument);
            this.lastProcessedIndex = beginIndex;
        } else {
            throw new ArgumentParserException("beginIndex=" + beginIndex + " is out of bounds. (args.length=" + args.length + ").");
        }

        this.lastProcessedIndex = beginIndex;
    }

}
