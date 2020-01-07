package de.arthurpicht.cli.argument;

import de.arthurpicht.cli.common.CLIParserException;

public class ArgumentParserVar extends ArgumentParser {

    private int minimalNrOfArgument;

    public ArgumentParserVar(int minimalNrOfArguments) {
        this.minimalNrOfArgument = minimalNrOfArguments;
    }

    @Override
    public void parse(String[] args, int beginIndex) throws ArgumentParserException {

//        this.isParsed = true;

        if (args.length < this.minimalNrOfArgument + beginIndex)
            throw new ArgumentParserException("minimalNrOfArguments = " + this.minimalNrOfArgument + " is out of bounds. Arguments remaining = " + (args.length - beginIndex));

        for(int i=beginIndex; i<args.length; i++) {
            String argument = args[i];
            this.argumentList.add(argument);
        }

        this.lastProcessedIndex = args.length - 1;

    }
}
