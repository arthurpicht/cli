package de.arthurpicht.cli.parameter;

public class ParametersMany extends Parameters {

    private int nrOfArguments;

    public ParametersMany(int nrOfArguments) {
        this.nrOfArguments = nrOfArguments;
    }

    @Override
    public ParameterParser getParameterParser() {
        return new ParameterParserMany(this.nrOfArguments);
    }
}
