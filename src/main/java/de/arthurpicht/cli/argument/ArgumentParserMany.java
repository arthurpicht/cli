package de.arthurpicht.cli.argument;

public class ArgumentParserMany extends ArgumentParser {

    private int nrOfArguments;

    public ArgumentParserMany(int nrOfArguments) {
        this.nrOfArguments = nrOfArguments;
    }

    @Override
    public void parse(String[] args, int beginIndex) throws ArgumentParserException {

        if (args.length < this.nrOfArguments + beginIndex)
            throw new ArgumentParserException(args, beginIndex, "Not enough arguments found. required: " + this.nrOfArguments + "; found: " + (args.length - beginIndex) + ".");

        for(int i = beginIndex; i < beginIndex+this.nrOfArguments; i++) {
            String argument = args[i];
            this.argumentList.add(argument);
        }

        this.lastProcessedIndex = beginIndex + this.nrOfArguments - 1;
    }
}
