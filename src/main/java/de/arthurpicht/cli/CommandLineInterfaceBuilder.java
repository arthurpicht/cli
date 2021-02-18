package de.arthurpicht.cli;

import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.tree.CommandTree;
import de.arthurpicht.cli.option.Options;

public class CommandLineInterfaceBuilder {

    private Options optionsGlobal;
    private CommandTree commandTree;
    private DefaultCommand defaultCommand;

    public CommandLineInterfaceBuilder() {
        this.optionsGlobal = null;
        this.commandTree = null;
        this.defaultCommand = null;
    }

    public CommandLineInterfaceBuilder withGlobalOptions(Options options) {
        this.optionsGlobal = options;
        return this;
    }

    public CommandLineInterfaceBuilder withCommands(Commands commands) {
        this.commandTree = commands.getCommandTree();
        this.defaultCommand = commands.getDefaultCommand();
        return this;
    }

    public CommandLineInterface build(String executableName) {
        CommandLineInterfaceDescription commandLineInterfaceDescription
                = new CommandLineInterfaceDescription(executableName);
        return createCommandLineInterface(commandLineInterfaceDescription);
    }

    public CommandLineInterface build(CommandLineInterfaceDescription commandLineInterfaceDescription) {
        return createCommandLineInterface(commandLineInterfaceDescription);
    }

    private CommandLineInterface createCommandLineInterface(CommandLineInterfaceDescription commandLineInterfaceDescription) {
        CommandLineInterfaceDefinition commandLineInterfaceDefinition = new CommandLineInterfaceDefinition(
                commandLineInterfaceDescription,
                this.optionsGlobal,
                this.commandTree,
                this.defaultCommand
        );
        return new CommandLineInterface(commandLineInterfaceDefinition);
    }
}
