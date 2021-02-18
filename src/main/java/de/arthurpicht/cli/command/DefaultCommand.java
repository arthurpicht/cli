package de.arthurpicht.cli.command;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.parameter.Parameters;

public class DefaultCommand {

    private final Parameters parameters;
    private final CommandExecutor commandExecutor;
    private final String description;

    public DefaultCommand(Parameters parameters, CommandExecutor commandExecutor, String description) {
        this.parameters = parameters;
        this.commandExecutor = commandExecutor;
        this.description = description;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    public String getDescription() {
        return description;
    }
}
