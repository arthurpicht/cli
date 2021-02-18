package de.arthurpicht.cli.command.tree;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.utils.core.strings.Strings;

public class CommandTerminator {

    private final Options specificOptions;
    private final Parameters parameters;
    private final CommandExecutor commandExecutor;
    private final String description;

    public CommandTerminator(Options specificOptions, Parameters parameters, CommandExecutor commandExecutor, String description) {
        this.specificOptions = specificOptions;
        this.parameters = parameters;
        this.commandExecutor = commandExecutor;
        this.description = description;
    }

    public boolean hasSpecificOptions() {
        return this.specificOptions != null;
    }

    public Options getSpecificOptions() {
        return specificOptions;
    }

    public boolean hasParameters() {
        return this.parameters != null;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public boolean hasCommandExecutor() {
        return this.commandExecutor != null;
    }

    public CommandExecutor getCommandExecutor() {
        return this.commandExecutor;
    }

    public boolean hasDescription() {
        return Strings.isSpecified(this.description);
    }

    public String getDescription() {
        return description;
    }
}
