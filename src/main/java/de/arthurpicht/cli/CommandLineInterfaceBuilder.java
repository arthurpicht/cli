package de.arthurpicht.cli;

import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.tree.CommandTree;
import de.arthurpicht.cli.common.CLIContext;
import de.arthurpicht.cli.option.Options;

import java.io.PrintStream;

public class CommandLineInterfaceBuilder {

    private Options optionsGlobal;
    private CommandTree commandTree;
    private DefaultCommand defaultCommand;
    private PrintStream out;
    private PrintStream errorOut;

    public CommandLineInterfaceBuilder() {
        this.optionsGlobal = null;
        this.commandTree = null;
        this.defaultCommand = null;
        this.out = System.out;
        this.errorOut = System.err;
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

    public CommandLineInterfaceBuilder withOut(PrintStream out) {
        this.out = out;
        return this;
    }

    public CommandLineInterfaceBuilder withErrorOut(PrintStream errorOut) {
        this.errorOut = errorOut;
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

        CLIContext.init(this.out, this.errorOut);

        CommandLineInterfaceDefinition commandLineInterfaceDefinition = new CommandLineInterfaceDefinition(
                commandLineInterfaceDescription,
                this.optionsGlobal,
                this.commandTree,
                this.defaultCommand
        );
        return new CommandLineInterface(commandLineInterfaceDefinition);
    }
}
