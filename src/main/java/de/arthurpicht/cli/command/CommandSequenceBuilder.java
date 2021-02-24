package de.arthurpicht.cli.command;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.command.exceptions.CommandSpecException;
import de.arthurpicht.cli.command.tree.Command;
import de.arthurpicht.cli.command.tree.OneCommand;
import de.arthurpicht.cli.command.tree.OpenCommand;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.utils.core.assertion.AssertMethodPrecondition;

import java.util.ArrayList;
import java.util.List;

public class CommandSequenceBuilder {

    private final List<Command> commandList;

    private Options specificOptions = null;
    private Parameters parameters = null;
    private CommandExecutor commandExecutor = null;
    private String description = null;
    private int helpPriority = Integer.MAX_VALUE;

    public CommandSequenceBuilder() {
        this.commandList = new ArrayList<>();
    }

    public CommandSequenceBuilder addCommand(String commandString) {
        AssertMethodPrecondition.methodParamNotNullAndNotEmpty("commandString", commandString);
        CommandsPrecondition.assertLegalCharacters(commandString);
        assertNotRestrictedByOpenCommand();

        Command command = new OneCommand(commandString);
        this.commandList.add(command);
        return this;
    }

    public CommandSequenceBuilder addCommands(String... commandStrings) {
        CommandSequenceBuilder commandSequenceBuilder = this;
        for (String commandString : commandStrings) {
            commandSequenceBuilder = addCommand(commandString);
        }
        return commandSequenceBuilder;
    }

    public CommandSequenceBuilder addOpen() {
        assertNotRestrictedByOpenCommand();
        Command command = new OpenCommand();
        this.commandList.add(command);
        return this;
    }

    public CommandSequenceBuilder withSpecificOptions(Options specificOptions) {
        AssertMethodPrecondition.parameterNotNull("specificOptions", specificOptions);
        this.specificOptions = specificOptions;
        return this;
    }

    public CommandSequenceBuilder withParameters(Parameters parameters) {
        AssertMethodPrecondition.parameterNotNull("parameters", parameters);
        this.parameters = parameters;
        return this;
    }

    public CommandSequenceBuilder withCommandExecutor(CommandExecutor commandExecutor) {
        AssertMethodPrecondition.parameterNotNull("commandExecutor", commandExecutor);
        this.commandExecutor = commandExecutor;
        return this;
    }

    public CommandSequenceBuilder withDescription(String description) {
        AssertMethodPrecondition.methodParamNotNullAndNotEmpty("description", description);
        this.description = description;
        return this;
    }

    public CommandSequenceBuilder withHelpPriority(int helpPriority) {
        this.helpPriority = helpPriority;
        return this;
    }

    public CommandSequence build() {
        assertNotEmpty();
        assertNoOpenCommandInCombinationWithParameters();
        return new CommandSequence(this.commandList, this.specificOptions, this.parameters, this.commandExecutor, this.description, this.helpPriority);
    }

    private void assertNotRestrictedByOpenCommand() {
        if (this.commandList.isEmpty()) return;
        Command lastCommand = this.commandList.get(this.commandList.size() - 1);
        if (lastCommand.isOpen())
            throw new CommandSpecException("No further commands are possible after open command.");
    }

    private void assertNotEmpty() {
        if (this.commandList.isEmpty())
            throw new CommandSpecException("Command Sequence must have at least one command.");
    }

    private void assertNoOpenCommandInCombinationWithParameters() {
        if (this.commandList.get(this.commandList.size() - 1).isOpen() && this.parameters != null)
            throw new CommandSpecException("Illegal combination of open command and parameters.");
    }

}
