package de.arthurpicht.cli;

import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.tree.CommandTree;
import de.arthurpicht.cli.common.PostProcessor;
import de.arthurpicht.cli.option.Options;

public class CliBuilder {

    private Options optionsGlobal;
    private CommandTree commandTree;
    private DefaultCommand defaultCommand;
    private boolean autoHelp;

    public CliBuilder() {
        this.optionsGlobal = new Options();
        this.commandTree = null;
        this.defaultCommand = null;
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
