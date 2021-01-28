package de.arthurpicht.cli.command;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;

public class DefaultCommandBuilder {

    private Options optionsSpecific;
    private Parameters parameters;
    private CommandExecutor commandExecutor;

    public DefaultCommandBuilder() {
        this.optionsSpecific = null;
        this.parameters = null;
        this.commandExecutor = null;
    }

    public DefaultCommandBuilder withSpecificOptions(Options optionsSpecific) {
        this.optionsSpecific = optionsSpecific;
        return this;
    }

    public DefaultCommandBuilder withParameters(Parameters parameters) {
        this.parameters = parameters;
        return this;
    }

    public DefaultCommandBuilder withCommandExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
        return this;
    }

    public DefaultCommand build() {
        return new DefaultCommand(this.optionsSpecific, this.parameters, this.commandExecutor);
    }

}
