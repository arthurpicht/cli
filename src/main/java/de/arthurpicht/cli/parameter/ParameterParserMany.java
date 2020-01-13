package de.arthurpicht.cli.parameter;

public class ParameterParserMany extends ParameterParser {

    private int nrOfArguments;

    public ParameterParserMany(int nrOfArguments) {
        this.nrOfArguments = nrOfArguments;
    }

    @Override
    public void parse(String[] args, int beginIndex) throws ParameterParserException {

        if (args.length < this.nrOfArguments + beginIndex)
            throw new ParameterParserException(args, beginIndex, "Not enough arguments found. required: " + this.nrOfArguments + "; found: " + (args.length - beginIndex) + ".");

        for(int i = beginIndex; i < beginIndex+this.nrOfArguments; i++) {
            String argument = args[i];
            this.argumentList.add(argument);
        }

        this.lastProcessedIndex = beginIndex + this.nrOfArguments - 1;
    }
}
