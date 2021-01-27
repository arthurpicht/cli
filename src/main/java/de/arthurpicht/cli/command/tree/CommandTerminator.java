package de.arthurpicht.cli.command.tree;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;

public class CommandTerminator {

    private Options specificOptions;
    private Parameters parameters;
    private CommandExecutor commandExecutor;

    public CommandTerminator() {
        this.specificOptions = null;
        this.parameters = null;
    }

    public CommandTerminator(Options specificOptions, Parameters parameters, CommandExecutor commandExecutor) {
        this.specificOptions = specificOptions;
        this.parameters = parameters;
        this.commandExecutor = commandExecutor;
    }

    public boolean hasSpecificOptions() {
        return this.specificOptions != null;
    }

    public Options getSpecificOptions() {
        return specificOptions;
    }

    public void setSpecificOptions(Options specificOptions) {
        this.specificOptions = specificOptions;
    }

    public boolean hasParameters() {
        return this.parameters != null;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public boolean hasCommandExecutor() {
        return this.commandExecutor != null;
    }

    public CommandExecutor getCommandExecutor() {
        return this.commandExecutor;
    }
}
