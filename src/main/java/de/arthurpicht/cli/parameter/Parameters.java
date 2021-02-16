package de.arthurpicht.cli.parameter;

public abstract class Parameters {

    public abstract ParameterParser getParameterParser();

    public abstract String getHelpUsageSubString();

    public abstract String getHelpString();

}
