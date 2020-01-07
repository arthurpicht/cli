package de.arthurpicht.cli.argument;

public class ArgumentParserMany extends ArgumentParser {

    private int nrOfArguments;

    public ArgumentParserMany(int nrOfArguments) {
        this.nrOfArguments = nrOfArguments;
    }

    @Override
    public void parse(String[] args, int beginIndex) throws ArgumentParserException {

//        this.isParsed = true;

        if (args.length < this.nrOfArguments + beginIndex)
            throw new ArgumentParserException("nrOfArguments = " + this.nrOfArguments + " is out of bounds. Arguments remaining = " + (args.length - beginIndex) + ".");

        for(int i = beginIndex; i < beginIndex+this.nrOfArguments; i++) {
            String argument = args[i];
            this.argumentList.add(argument);
        }

        this.lastProcessedIndex = beginIndex + this.nrOfArguments - 1;
    }
}
