package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CommandLineInterfaceResultBuilder;

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
    public ParameterParserOne getParameterParser(CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder) {
        return new ParameterParserOne(commandLineInterfaceResultBuilder);
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
