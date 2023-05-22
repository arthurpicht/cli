package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CliResultBuilder;
import de.arthurpicht.utils.core.strings.Strings;

public class ParametersMin extends Parameters {

    private final int minimalNrOfParameters;
    private final String name;
    private final String description;

    public ParametersMin(int minimalNrOfParameters) {
        if (minimalNrOfParameters < 0)
            throw new IllegalArgumentException("Minimal number of arguments: " + minimalNrOfParameters + ". " +
                    "Expected as >= 0");

        this.minimalNrOfParameters = minimalNrOfParameters;
        this.name = Parameter.DEFAULT_NAME;
        this.description = "";
    }

    public ParametersMin(int minimalNrOfParameters, String name, String description) {
        if (minimalNrOfParameters < 0)
            throw new IllegalArgumentException("Minimal number of arguments: " + minimalNrOfParameters + ". " +
                    "Expected as >= 0");

        this.minimalNrOfParameters = minimalNrOfParameters;
        this.name = Strings.isUnspecified(name) ? Parameter.DEFAULT_NAME : name;
        this.description = Strings.isUnspecified(description) ? "" : description;
    }

    public boolean hasDescription() {
        return Strings.isSpecified(this.description);
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public ParameterParser getParameterParser(CliResultBuilder cliResultBuilder, String executableName) {
        return new ParameterParserMin(this.minimalNrOfParameters, cliResultBuilder, executableName);
    }

    @Override
    public String getHelpUsageSubString() {
        return this.minimalNrOfParameters + "..n*<" + this.name + ">";
    }

}
