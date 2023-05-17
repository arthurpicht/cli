package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CliResultBuilder;
import de.arthurpicht.utils.core.strings.Strings;

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

    public boolean hasDescription() {
        return Strings.isSpecified(this.parameter.getDescription());
    }

    public String getDescription() {
        return this.parameter.getDescription();
    }

    @Override
    public ParameterParserOne getParameterParser(CliResultBuilder cliResultBuilder, String executableName) {
        return new ParameterParserOne(cliResultBuilder, executableName);
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
