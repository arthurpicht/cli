package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CliResultBuilder;

public class ParametersOne extends Parameters {

    private final Parameter parameter;

    public ParametersOne() {
        this.parameter = new Parameter(Parameter.DEFAULT_NAME, "");
    }

    public ParametersOne(Parameter parameter) {
        this.parameter = parameter;
    }

    public ParametersOne(String name, String description) {
        this.parameter = new Parameter(name, description);
    }

    @Override
    public ParameterParserOne getParameterParser(CliResultBuilder cliResultBuilder) {
        return new ParameterParserOne(cliResultBuilder);
    }

    @Override
    public String getHelpUsageSubString() {
        return this.parameter.getUsageString();
    }

    @Override
    public String getHelpString() {
        return this.parameter.getHelpString();
    }
}
