package de.arthurpicht.cli;

import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.tree.CommandTree;
import de.arthurpicht.cli.common.CLISpecificationException;
import de.arthurpicht.cli.option.Options;

public class CliDefinition {

    private final CliDescription cliDescription;
    private final Options globalOptions;
    private final CommandTree commandTree;
    private final DefaultCommand defaultCommand;

    public CliDefinition(CliDescription cliDescription, Options globalOptions, CommandTree commandTree, DefaultCommand defaultCommand) {
        this.cliDescription = cliDescription;
        this.globalOptions = globalOptions;
        this.commandTree = commandTree;
        this.defaultCommand = defaultCommand;

        if (!hasCommands() && !hasDefaultCommand())
            throw new CLISpecificationException("Specification of at least commands or default command is expected.");
    }

    public CliDescription getCliDescription() {
        return cliDescription;
    }

    public boolean hasGlobalOptions() {
        return (this.globalOptions != null && !this.globalOptions.isEmpty());
    }

    public Options getGlobalOptions() {
        return globalOptions;
    }

    public boolean hasDefaultCommand() {
        return this.defaultCommand != null;
    }

    public DefaultCommand getDefaultCommand() {
        return defaultCommand;
    }

    public boolean hasCommands() {
        return (this.commandTree != null && !this.commandTree.isEmpty());
    }

    public CommandTree getCommandTree() {
        return commandTree;
    }

}
