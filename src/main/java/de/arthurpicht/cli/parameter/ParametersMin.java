package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CommandLineInterfaceResultBuilder;
import de.arthurpicht.cli.help.HelpFormatter;
import de.arthurpicht.utils.core.strings.Strings;

public class ParametersMin extends Parameters {

    private final int minimalNrOfParameters;
    private final String name;
    private final String description;

    public ParametersMin(int minimalNrOfParameters) {
        if (minimalNrOfParameters < 0)
            throw new IllegalArgumentException("Minimal number of arguments: " + minimalNrOfParameters + ". Expected as >= 0");

        this.minimalNrOfParameters = minimalNrOfParameters;
        this.name = Parameter.DEFAULT_NAME;
        this.description = "";
    }

    public ParametersMin(int minimalNrOfParameters, String name, String description) {

        if (minimalNrOfParameters < 0)
            throw new IllegalArgumentException("Minimal number of arguments: " + minimalNrOfParameters + ". Expected as >= 0");

        this.minimalNrOfParameters = minimalNrOfParameters;

        if (Strings.isUnspecified(name)) {
            this.name = Parameter.DEFAULT_NAME;
        } else {
            this.name = name;
        }

        if (Strings.isUnspecified(description)) {
            this.description = "";
        } else {
            this.description = description;
        }
    }

    @Override
    public ParameterParser getParameterParser(CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder) {
        return new ParameterParserMin(this.minimalNrOfParameters, commandLineInterfaceResultBuilder);
    }

    @Override
    public String getHelpUsageSubString() {
        return this.minimalNrOfParameters + "...n*<" + this.name + ">";
    }

    @Override
    public String getHelpString() {
        return HelpFormatter.formatStringsToCols(
                getHelpUsageSubString(),
                this.description);
    }
}