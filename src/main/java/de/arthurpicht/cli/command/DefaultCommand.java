package de.arthurpicht.cli.command;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.parameter.Parameters;

public class DefaultCommand {

    private final Parameters parameters;
    private final CommandExecutor commandExecutor;

    public DefaultCommand(Parameters parameters, CommandExecutor commandExecutor) {
        this.parameters = parameters;
        this.commandExecutor = commandExecutor;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

}
