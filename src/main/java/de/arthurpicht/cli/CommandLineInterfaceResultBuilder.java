package de.arthurpicht.cli;

import de.arthurpicht.cli.CommandLineInterfaceResult.Status;
import de.arthurpicht.cli.command.CommandParserResult;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.ParameterParserResult;
import de.arthurpicht.cli.parameter.Parameters;

public class CommandLineInterfaceResultBuilder {

    private OptionParserResult optionParserResultGlobal;
    private CommandParserResult commandParserResult;
    private OptionParserResult optionParserResultSpecific;
    private ParameterParserResult parameterParserResult;

    public CommandLineInterfaceResultBuilder() {
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

    public CommandLineInterfaceResult build(Status status) {

        return new CommandLineInterfaceResult(
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

//    public void debugOut() {
//        System.out.println("has globalOptions   ? " + !this.optionParserResultGlobal.isEmpty());
//        System.out.println("Commands            : " + Strings.listing(this.commandParserResult.getCommandStringList(), " "));
//        System.out.println("has specificOptions ? " + !this.optionParserResultSpecific.isEmpty());
//        System.out.println("Parameters          : " + Strings.listing(this.parameterParserResult.getParameterList(), " "));
//    }

}
