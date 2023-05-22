package de.arthurpicht.cli;

import de.arthurpicht.cli.CliResult.Status;
import de.arthurpicht.cli.command.CommandParserResult;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.ParameterParserResult;
import de.arthurpicht.cli.parameter.Parameters;

public class CliResultBuilder {

    private OptionParserResult optionParserResultGlobal;
    private CommandParserResult commandParserResult;
    private OptionParserResult optionParserResultSpecific;
    private ParameterParserResult parameterParserResult;

    public CliResultBuilder() {
        this.optionParserResultGlobal = new OptionParserResult();
        this.commandParserResult = new CommandParserResult();
        this.optionParserResultSpecific = new OptionParserResult();
        this.parameterParserResult = new ParameterParserResult();
    }

    public void withOptionParserResultGlobal(OptionParserResult optionParserResultGlobal) {
        this.optionParserResultGlobal = optionParserResultGlobal;
    }

    public void withCommandParserResult(CommandParserResult commandParserResult) {
        this.commandParserResult = commandParserResult;
    }

    public void withOptionParserResultSpecific(OptionParserResult optionParserResultSpecific) {
        this.optionParserResultSpecific = optionParserResultSpecific;
    }

    public void withParameterParserResult(ParameterParserResult parameterParserResult) {
        this.parameterParserResult = parameterParserResult;
    }

    public CliResult build(Status status) {

        return new CliResult(
                status,
                this.optionParserResultGlobal,
                this.commandParserResult,
                this.optionParserResultSpecific,
                this.parameterParserResult
        );
    }

    public boolean hasSpecificOptions() {
        return this.commandParserResult.hasSpecificOptions();
    }

    public Options getSpecificOptions() {
        return this.commandParserResult.getSpecificOptions();
    }

    public boolean hasParameters() {
        return this.commandParserResult.hasParameters();
    }

    public Parameters getParameters() {
        return this.commandParserResult.getParameters();
    }

}
