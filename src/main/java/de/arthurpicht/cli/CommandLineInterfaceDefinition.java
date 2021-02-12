package de.arthurpicht.cli;

import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.tree.CommandTree;
import de.arthurpicht.cli.common.CLISpecificationException;
import de.arthurpicht.cli.option.Options;

public class CommandLineInterfaceDefinition {

    private final Options globalOptions;
    private final CommandTree commandTree;
    private final DefaultCommand defaultCommand;

    public CommandLineInterfaceDefinition(Options globalOptions, CommandTree commandTree, DefaultCommand defaultCommand) {
        this.globalOptions = globalOptions;
        this.commandTree = commandTree;
        this.defaultCommand = defaultCommand;

        if (!hasDefinitionsOfCommands() && !hasDefaultCommand())
            throw new CLISpecificationException("Specification of at least commands or default command is expected.");
    }

    public boolean hasDefinitionOfGlobalOptions() {
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

    public boolean hasDefinitionsOfCommands() {
        return (this.commandTree != null && !this.commandTree.isEmpty());
    }

    public CommandTree getCommandTree() {
        return commandTree;
    }

}
