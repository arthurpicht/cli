package de.arthurpicht.cli.parameter;

public class ParametersOne extends Parameters {

    public ParametersOne() {
        super();
    }

    @Override
    public ParameterParser getParameterParser() {
        return new ParameterParserOne();
    }
}
