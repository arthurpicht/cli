package de.arthurpicht.cli.command;

import de.arthurpicht.cli.command.exceptions.AmbiguousCommandException;
import de.arthurpicht.cli.command.exceptions.IllegalCommandException;
import de.arthurpicht.cli.command.exceptions.InsufficientNrOfCommandsException;
import de.arthurpicht.cli.common.ArgumentIterator;
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
    public void parse(ArgumentIterator argumentIterator) throws IllegalCommandException, AmbiguousCommandException, InsufficientNrOfCommandsException {

//        this.lastProcessedIndex = beginIndex;

        Set<Command> curCommandSet = this.commands.getRootCommands();

        Command lastCommand = null;

//        for (int i = beginIndex; i < args.length; i++) {
        while (argumentIterator.hasNext()) {

            if (curCommandSet.isEmpty()) {
//                this.lastProcessedIndex = i - 1;
                this.specificOptions = lastCommand.getSpecificOptions();
                this.parameters = lastCommand.getParameters();
                return;
            }

            String curArgument = argumentIterator.getNext();

            CommandMatcher commandMatcher = new CommandMatcher(curCommandSet, curArgument, true);
            if (commandMatcher.hasMatchingCommand()) {
                RecognizedCommand matchingCommand = commandMatcher.getMatchingCommand();
                this.commandStringList.add(matchingCommand.getCommandName());
                curCommandSet = matchingCommand.getCommand().getNext();
//                this.lastProcessedIndex = i;
                lastCommand = matchingCommand.getCommand();
            } else {
                if (commandMatcher.hasCandidates()) {
                    throw AmbiguousCommandException.createInstance(argumentIterator, commandMatcher.getMatchingCandidates());
                } else {
                    throw IllegalCommandException.createInstance(argumentIterator, curCommandSet);
                }
            }
        }

        if (!curCommandSet.isEmpty()) {
            // insufficient number of arguments
//            int argumentIndex;
//            if (!argumentIterator.hasNext()) {
//                // no further arguments
//                argumentIndex = argumentIterator.getIndex();
//            } else {
//                // has next argument, is specific option or parameter
//                argumentIndex = argumentIterator.getIndex() + 1;
//            }
            throw InsufficientNrOfCommandsException.createInstance(argumentIterator, curCommandSet);
        }

        this.specificOptions = lastCommand.getSpecificOptions();
        this.parameters = lastCommand.getParameters();
    }

}
