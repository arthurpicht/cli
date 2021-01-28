package de.arthurpicht.cli.command;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;

public class DefaultCommand {

    private final Options optionsSpecific;
    private final Parameters parameters;
    private final CommandExecutor commandExecutor;

    public DefaultCommand(Options optionsSpecific, Parameters parameters, CommandExecutor commandExecutor) {
        this.optionsSpecific = optionsSpecific;
        this.parameters = parameters;
        this.commandExecutor = commandExecutor;
    }

    public Options getOptionsSpecific() {
        return optionsSpecific;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

}
