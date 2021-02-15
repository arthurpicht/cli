package de.arthurpicht.cli;

import de.arthurpicht.cli.command.CommandParserResult;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.parameter.ParameterParserResult;

import java.util.Collections;
import java.util.List;

public class CommandLineInterfaceResult {

    private final OptionParserResult optionParserResultGlobal;
    private final CommandParserResult commandParserResult;
    private final OptionParserResult optionParserResultSpecific;
    private final ParameterParserResult parameterParserResult;

    public CommandLineInterfaceResult(OptionParserResult optionParserResultGlobal, CommandParserResult commandParserResult, OptionParserResult optionParserResultSpecific, ParameterParserResult parameterParserResult, CommandExecutor commandExecutor) {

        if (optionParserResultGlobal == null) throw new IllegalArgumentException("Method parameter is null.");
        if (commandParserResult == null) throw new IllegalArgumentException("Method parameter is null.");
        if (optionParserResultSpecific == null) throw new IllegalArgumentException("Method parameter is null.");
        if (parameterParserResult == null) throw new IllegalArgumentException("Method parameter is null.");

        this.optionParserResultGlobal = optionParserResultGlobal;
        this.commandParserResult = commandParserResult;
        this.optionParserResultSpecific = optionParserResultSpecific;
        this.parameterParserResult = parameterParserResult;
    }

    public OptionParserResult getOptionParserResultGlobal() {
        return this.optionParserResultGlobal;
    }

    public CommandParserResult getCommandParserResult() {
        return this.commandParserResult;
    }

    public OptionParserResult getOptionParserResultSpecific() {
        return this.optionParserResultSpecific;
    }

    public ParameterParserResult getParameterParserResult() {
        return parameterParserResult;
    }

}
