package de.arthurpicht.cli;

import de.arthurpicht.cli.command.CommandParserResult;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.parameter.ParameterParserResult;

public class CliResult {

    public enum Status {COMPLETE, BROKEN, TEST}

    private final Status status;
    private final OptionParserResult optionParserResultGlobal;
    private final CommandParserResult commandParserResult;
    private final OptionParserResult optionParserResultSpecific;
    private final ParameterParserResult parameterParserResult;

    public CliResult(Status status, OptionParserResult optionParserResultGlobal, CommandParserResult commandParserResult, OptionParserResult optionParserResultSpecific, ParameterParserResult parameterParserResult) {
        if (status == null) throw new IllegalArgumentException("Method parameter is null.");
        if (optionParserResultGlobal == null) throw new IllegalArgumentException("Method parameter is null.");
        if (commandParserResult == null) throw new IllegalArgumentException("Method parameter is null.");
        if (optionParserResultSpecific == null) throw new IllegalArgumentException("Method parameter is null.");
        if (parameterParserResult == null) throw new IllegalArgumentException("Method parameter is null.");

        this.status = status;
        this.optionParserResultGlobal = optionParserResultGlobal;
        this.commandParserResult = commandParserResult;
        this.optionParserResultSpecific = optionParserResultSpecific;
        this.parameterParserResult = parameterParserResult;
    }

    public Status getStatus() {
        return status;
    }

    public boolean hasGlobalOptions() {
        return this.optionParserResultGlobal.hasOptions();
    }

    public OptionParserResult getOptionParserResultGlobal() {
        return this.optionParserResultGlobal;
    }

    public CommandParserResult getCommandParserResult() {
        return this.commandParserResult;
    }

    public boolean hasSpecificOptions() {
        return this.optionParserResultSpecific.hasOptions();
    }

    public OptionParserResult getOptionParserResultSpecific() {
        return this.optionParserResultSpecific;
    }

    public boolean hasParameters() {
        return !this.getParameterParserResult().isEmpty();
    }

    public ParameterParserResult getParameterParserResult() {
        return parameterParserResult;
    }

}
