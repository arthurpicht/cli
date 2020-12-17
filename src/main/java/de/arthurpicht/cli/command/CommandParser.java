package de.arthurpicht.cli.command;

import de.arthurpicht.cli.command.exceptions.AmbiguousCommandException;
import de.arthurpicht.cli.command.exceptions.IllegalCommandException;
import de.arthurpicht.cli.command.exceptions.InsufficientNrOfCommandsException;
import de.arthurpicht.cli.common.Parser;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.utils.core.assertion.AssertMethodPrecondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommandParser extends Parser {

    private Commands commands;
    private List<String> commandStringList;
    private Options specificOptions;
    private Parameters parameters;

    public CommandParser(Commands commands) {
        this.commands = commands;
        this.commandStringList = new ArrayList<>();
        this.specificOptions = null;
        this.parameters = null;
    }

    public List<String> getCommandStringList() {
        return this.commandStringList;
    }

    public Options getSpecificOptions() {
        return this.specificOptions != null ? this.specificOptions : new Options();
    }

    public Parameters getParameters() {
        return this.parameters;
    }

    @Override
    public void parse(String[] args, int beginIndex) throws IllegalCommandException, AmbiguousCommandException, InsufficientNrOfCommandsException {

        AssertMethodPrecondition.parameterNotNull("args", args);

        this.lastProcessedIndex = beginIndex;

        Set<Command> curCommandSet = this.commands.getRootCommands();

        Command lastCommand = null;

        for (int i = beginIndex; i < args.length; i++) {

            if (curCommandSet.isEmpty()) {
                this.lastProcessedIndex = i - 1;
                this.specificOptions = lastCommand.getSpecificOptions();
                this.parameters = lastCommand.getParameters();
                return;
            }

            CommandMatcher commandMatcher = new CommandMatcher(curCommandSet, args[i], true);
            if (commandMatcher.hasMatchingCommand()) {
                RecognizedCommand matchingCommand = commandMatcher.getMatchingCommand();
                this.commandStringList.add(matchingCommand.getCommandName());
                curCommandSet = matchingCommand.getCommand().getNext();
                this.lastProcessedIndex = i;
                lastCommand = matchingCommand.getCommand();
            } else {
                if (commandMatcher.hasCandidates()) {
                    throw AmbiguousCommandException.createInstance(args, i, commandMatcher.getMatchingCandidates());
                } else {
                    throw IllegalCommandException.createInstance(args, i, curCommandSet);
                }
            }
        }

        // todo optional-flag
        if (!curCommandSet.isEmpty()) {
            int argumentIndex = this.lastProcessedIndex + 1;
            if (beginIndex == args.length) {
                argumentIndex = this.lastProcessedIndex;
            }
            throw InsufficientNrOfCommandsException.createInstance(args, argumentIndex, curCommandSet);
        }

        this.specificOptions = lastCommand.getSpecificOptions();
        this.parameters = lastCommand.getParameters();
    }

}
