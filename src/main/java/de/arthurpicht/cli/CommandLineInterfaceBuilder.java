package de.arthurpicht.cli;

import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;

public class CommandLineInterfaceBuilder {

    private Options optionsGlobal;
    private Commands commands;
    private Parameters parameters;

    public CommandLineInterfaceBuilder() {
        this.optionsGlobal = null;
        this.commands = null;
        this.parameters = null;
    }

    public CommandLineInterfaceBuilder withGlobalOptions(Options options) {
        this.optionsGlobal = options;
        return this;
    }

    public CommandLineInterfaceBuilder withCommands(Commands commands) {
        this.commands = commands;
        return this;
    }

    public CommandLineInterfaceBuilder withParameters(Parameters parameters) {
        this.parameters = parameters;
        return this;
    }

    public CommandLineInterface build() {
        return new CommandLineInterface(this.optionsGlobal, this.commands, this.parameters);
    }


}
