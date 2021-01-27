package de.arthurpicht.cli;

import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.option.Options;

public class CommandLineInterfaceBuilder {

    private Options optionsGlobal;
    private Commands commands;

    public CommandLineInterfaceBuilder() {
        this.optionsGlobal = null;
        this.commands = null;
    }

    public CommandLineInterfaceBuilder withGlobalOptions(Options options) {
        this.optionsGlobal = options;
        return this;
    }

    public CommandLineInterfaceBuilder withCommands(Commands commands) {
        this.commands = commands;
        return this;
    }

    public CommandLineInterface build() {
        return new CommandLineInterface(this.optionsGlobal, this.commands);
    }

}
