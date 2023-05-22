package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CliResultBuilder;

public abstract class Parameters {

    public abstract ParameterParser getParameterParser(CliResultBuilder cliResultBuilder, String executableName);

    public abstract String getHelpUsageSubString();

//    public abstract String getHelpString();

}
