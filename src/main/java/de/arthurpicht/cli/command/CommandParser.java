package de.arthurpicht.cli.command;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.command.exceptions.AmbiguousCommandException;
import de.arthurpicht.cli.command.exceptions.IllegalCommandException;
import de.arthurpicht.cli.command.exceptions.InsufficientNrOfCommandsException;
import de.arthurpicht.cli.command.tree.Command;
import de.arthurpicht.cli.command.tree.CommandTree;
import de.arthurpicht.cli.command.tree.CommandTreeIterator;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Parser;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CommandParser extends Parser {

    private final CommandTree commandTree;
    private final DefaultCommand defaultCommand;

    private final List<String> commandStringList;
    private Options specificOptions;
    private Parameters parameters;
    private CommandExecutor commandExecutor;

    public CommandParser(CommandTree commandTree, DefaultCommand defaultCommand) {
        this.commandTree = commandTree;
        this.defaultCommand = defaultCommand;

        this.commandStringList = new ArrayList<>();
        this.specificOptions = null;
        this.parameters = null;
        this.commandExecutor = null;
    }

//    public List<String> getCommandStringList() {
//        return this.commandStringList;
//    }
//
//    public Options getSpecificOptions() {
//        return this.specificOptions != null ? this.specificOptions : new Options();
//    }
//
//    public Parameters getParameters() {
//        return this.parameters;
//    }
//
//    public CommandExecutor getCommandExecutor() {
//        return this.commandExecutor;
//    }

    @Override
    public CommandParserResult getParserResult() {
        return new CommandParserResult(
                this.commandStringList,
                this.specificOptions,
                this.parameters,
                this.commandExecutor
        );
    }

    @Override
    public void parse(ArgumentIterator argumentIterator) throws IllegalCommandException, AmbiguousCommandException, InsufficientNrOfCommandsException {

        CommandTreeIterator commandTreeIterator = new CommandTreeIterator(this.commandTree);

        Set<Command> commandCandidates = commandTreeIterator.getCommandCandidates();
        if (commandCandidates.isEmpty()) throw new RuntimeException("CommandParser.parse must not be called with empty command tree.");

        if (!argumentIterator.hasNext() && this.defaultCommand != null) {
            setPropertiesFromDefaultCommand(this.defaultCommand);
            return;
        }

        Command lastCommand = null;

        while (argumentIterator.hasNext()) {

            String curArgument = argumentIterator.getNext();

            if (curArgument.equals("--")) {
                if (lastCommand != null && lastCommand.isTerminated()) {
                    setPropertiesFromFoundCommand(lastCommand);
                    return;
                }
                throw IllegalCommandException.createInstanceForDoubleDash(argumentIterator, commandCandidates);
            }
            if (curArgument.startsWith("-")) {
                if (lastCommand != null && lastCommand.isTerminated()) {
                    setPropertiesFromFoundCommand(lastCommand);
                    argumentIterator.getPrevious();
                    return;
                }
                if (lastCommand != null)
                    throw InsufficientNrOfCommandsException.createInstance(argumentIterator, commandCandidates);
                if (this.defaultCommand != null)
                    throw InsufficientNrOfCommandsException.createInstanceWithNoCommandAsOption(argumentIterator, commandCandidates);
                throw InsufficientNrOfCommandsException.createInstance(argumentIterator, commandCandidates);
            }

            CommandMatcher commandMatcher = new CommandMatcher(commandCandidates, curArgument, true);
            if (commandMatcher.hasMatchingCommand()) {
                RecognizedCommand matchingCommand = commandMatcher.getMatchingCommand();
                this.commandStringList.add(matchingCommand.getCommandName());
                commandTreeIterator.stepForward(matchingCommand.getCommand());
                commandCandidates = commandTreeIterator.getCommandCandidates();
                lastCommand = matchingCommand.getCommand();
            } else {
                if (commandMatcher.hasCandidates()) {
                    throw AmbiguousCommandException.createInstance(argumentIterator, commandMatcher.getMatchingCandidates());
                } else {
                    throw IllegalCommandException.createInstance(argumentIterator, commandCandidates);
                }
            }

            if (commandCandidates.isEmpty()) {
                setPropertiesFromFoundCommand(Objects.requireNonNull(lastCommand));
                return;
            }

        }

        if (!commandCandidates.isEmpty()) {
            if (lastCommand == null || !lastCommand.isTerminated())
                throw InsufficientNrOfCommandsException.createInstance(argumentIterator, commandCandidates);
            setPropertiesFromFoundCommand(lastCommand);
            return;
        }

        setPropertiesFromFoundCommand(Objects.requireNonNull(lastCommand));
    }

    private void setPropertiesFromFoundCommand(Command command) {
        this.specificOptions = command.getCommandTerminator().getSpecificOptions();
        this.parameters = command.getCommandTerminator().getParameters();
        this.commandExecutor = command.getCommandTerminator().getCommandExecutor();
    }

    private void setPropertiesFromDefaultCommand(DefaultCommand defaultCommand) {
        this.specificOptions = null;
        this.parameters = defaultCommand.getParameters();
        this.commandExecutor = defaultCommand.getCommandExecutor();
    }

}
