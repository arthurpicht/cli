package de.arthurpicht.cli.parameter;

public class ParameterParserVar extends ParameterParser {

    private int minimalNrOfArgument;

    public ParameterParserVar(int minimalNrOfArguments) {
        this.minimalNrOfArgument = minimalNrOfArguments;
    }

    @Override
    public void parse(String[] args, int beginIndex) throws ParameterParserException {

        if (args.length < this.minimalNrOfArgument + beginIndex) {
            int nrFoundArgs = args.length - beginIndex;
            throw new ParameterParserException(args, beginIndex + nrFoundArgs, "Illegal number of arguments. Minimal number expected: " + this.minimalNrOfArgument + ", found: " + nrFoundArgs + ".");
        }

        for(int i=beginIndex; i<args.length; i++) {
            String argument = args[i];
            this.parameterList.add(argument);
        }

        this.lastProcessedIndex = args.length - 1;

    }
}
