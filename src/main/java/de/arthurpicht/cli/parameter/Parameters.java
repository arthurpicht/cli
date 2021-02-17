package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CommandLineInterfaceResultBuilder;

public abstract class Parameters {

    public abstract ParameterParser getParameterParser(CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder);

    public abstract String getHelpUsageSubString();

    public abstract String getHelpString();

}
