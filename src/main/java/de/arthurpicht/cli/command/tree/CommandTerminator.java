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
    private final int helpPriority;

    public CommandTerminator(Options specificOptions, Parameters parameters, CommandExecutor commandExecutor, String description, int helpPriority) {
        this.specificOptions = specificOptions;
        this.parameters = parameters;
        this.commandExecutor = commandExecutor;
        this.description = description;
        this.helpPriority = helpPriority;
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

    public int getHelpPriority() {
        return helpPriority;
    }
}
