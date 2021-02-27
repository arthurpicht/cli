package de.arthurpicht.cli;

import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.tree.CommandTree;
import de.arthurpicht.cli.common.CLIContext;
import de.arthurpicht.cli.common.PostProcessor;
import de.arthurpicht.cli.option.Options;

import java.io.PrintStream;

public class CliBuilder {

    private Options optionsGlobal;
    private CommandTree commandTree;
    private DefaultCommand defaultCommand;
    private PrintStream out;
    private PrintStream errorOut;
    private boolean autoHelp;

    public CliBuilder() {
        this.optionsGlobal = new Options();
        this.commandTree = null;
        this.defaultCommand = null;
        this.out = System.out;
        this.errorOut = System.err;
        this.autoHelp = false;
    }

    public CliBuilder withGlobalOptions(Options options) {
        this.optionsGlobal = options;
        return this;
    }

    public CliBuilder withCommands(Commands commands) {
        this.commandTree = commands.getCommandTree();
        this.defaultCommand = commands.getDefaultCommand();
        return this;
    }

    public CliBuilder withOut(PrintStream out) {
        this.out = out;
        return this;
    }

    public CliBuilder withErrorOut(PrintStream errorOut) {
        this.errorOut = errorOut;
        return this;
    }

    public CliBuilder withAutoHelp() {
        this.autoHelp = true;
        return this;
    }

    public Cli build(String executableName) {
        CliDescription cliDescription
                = new CliDescription(executableName);
        return createCli(cliDescription);
    }

    public Cli build(CliDescription cliDescription) {
        return createCli(cliDescription);
    }

    private Cli createCli(CliDescription cliDescription) {

        CLIContext.init(this.out, this.errorOut);

        if (this.autoHelp) {
            PostProcessor.addAutoHelp(this.optionsGlobal, this.commandTree);
        }

        CliDefinition cliDefinition = new CliDefinition(
                cliDescription,
                this.optionsGlobal,
                this.commandTree,
                this.defaultCommand
        );
        return new Cli(cliDefinition);
    }
}
