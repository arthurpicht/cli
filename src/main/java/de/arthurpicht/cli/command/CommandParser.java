package de.arthurpicht.cli.command;

import de.arthurpicht.cli.common.Parser;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.utils.core.assertion.AssertMethodPrecondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommandParser extends Parser {

    private Commands commands;
    private List<String> commandStringList;
    private Options specificOptions;

    public CommandParser(Commands commands) {
        this.commands = commands;
        this.commandStringList = new ArrayList<>();
        this.specificOptions = null;
    }

    public List<String> getCommandStringList() {
        return this.commandStringList;
    }

    public Options getSpecificOptions() {
        return this.specificOptions != null ? this.specificOptions : new Options();
    }

    @Override
    public void parse(String[] args, int beginIndex) throws CommandSyntaxException {

        AssertMethodPrecondition.parameterNotNull("args", args);

        this.lastProcessedIndex = beginIndex;

        Set<Command> curCommandSet = this.commands.getRootCommands();

        Command lastCommand = null;

        for (int i = beginIndex; i < args.length; i++) {

            if (curCommandSet.isEmpty()) {
                this.lastProcessedIndex = i - 1;
                this.specificOptions = lastCommand.getSpecificOptions();
                return;
            }

            Command matchingCommand = CommandsHelper.findMatchingCommand(curCommandSet, args[i]);
            if (matchingCommand != null) {
                this.commandStringList.add(args[i]);
                curCommandSet = matchingCommand.getNext();
                this.lastProcessedIndex = i;
                lastCommand = matchingCommand;
            } else {
                throw new CommandSyntaxException(args, i, "No definition found for '" + args[i] + "'. Possible commands are: " + CommandsHelper.toFormattedList(curCommandSet) + ".");
            }
        }

        // todo optional-flag
        if (!curCommandSet.isEmpty()) {
            int argumentIndex = this.lastProcessedIndex + 1;
            if (beginIndex == args.length) {
                argumentIndex = this.lastProcessedIndex;
            }
            throw new CommandSyntaxException(args, argumentIndex, "Insufficient number of commands. Next command is one of: " + CommandsHelper.toFormattedList(curCommandSet) + ".");
        }

        this.specificOptions = lastCommand.getSpecificOptions();

    }
}
