package de.arthurpicht.cli.parameter;

public class ParametersVar extends Parameters {

    private final int minimalNrOfArguments;

    public ParametersVar(int minimalNrOfArguments) {
        this.minimalNrOfArguments = minimalNrOfArguments;
    }

    @Override
    public ParameterParser getParameterParser() {
        return new ParameterParserVar(this.minimalNrOfArguments);
    }
}
