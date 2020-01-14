package de.arthurpicht.cli.parameter;

public class ParameterParserVar extends ParameterParser {

    private int minimalNrOfArgument;

    public ParameterParserVar(int minimalNrOfArguments) {
        this.minimalNrOfArgument = minimalNrOfArguments;
    }

    @Override
    public void parse(String[] args, int beginIndex) throws ParameterParserException {

//        this.isParsed = true;

        if (args.length < this.minimalNrOfArgument + beginIndex)
            throw new ParameterParserException(args, beginIndex, "minimalNrOfArguments = " + this.minimalNrOfArgument + " is out of bounds. Arguments remaining = " + (args.length - beginIndex));

        for(int i=beginIndex; i<args.length; i++) {
            String argument = args[i];
            this.parameterList.add(argument);
        }

        this.lastProcessedIndex = args.length - 1;

    }
}
