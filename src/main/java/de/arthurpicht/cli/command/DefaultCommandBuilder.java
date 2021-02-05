package de.arthurpicht.cli.command;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.parameter.Parameters;

public class DefaultCommandBuilder {

    private Parameters parameters;
    private CommandExecutor commandExecutor;

    public DefaultCommandBuilder() {
        this.parameters = null;
        this.commandExecutor = null;
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
        return new DefaultCommand(this.parameters, this.commandExecutor);
    }

}
