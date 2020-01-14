package de.arthurpicht.cli.command;

import de.arthurpicht.cli.common.Parser;
import de.arthurpicht.utils.core.assertion.AssertMethodPrecondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommandParser extends Parser {

    private Commands commands;
    private List<String> commandStringList;

    public CommandParser(Commands commands) {
        this.commands = commands;
        this.commandStringList = new ArrayList<>();
    }

    public List<String> getCommandStringList() {
        return this.commandStringList;
    }

    @Override
    public void parse(String[] args, int beginIndex) throws CommandSyntaxException {

        AssertMethodPrecondition.parameterNotNull("args", args);

        this.lastProcessedIndex = beginIndex;

        Set<Command> curCommandSet = this.commands.getRootCommands();

//        if (curCommandSet.isEmpty()) {
//            this.lastProcessedIndex = beginIndex - 1;
//            return;
//        }

        for (int i = beginIndex; i < args.length; i++) {

            if (curCommandSet.isEmpty()) {
                this.lastProcessedIndex = i - 1;
                return;
//                throw new CommandSyntaxError("No definition found for: " + args[i]);
            }

            Command matchingCommand = CommandsHelper.findMatchingCommand(curCommandSet, args[i]);
            if (matchingCommand != null) {
                this.commandStringList.add(args[i]);
                curCommandSet = matchingCommand.getNext();
                this.lastProcessedIndex = i;
            } else {
                throw new CommandSyntaxException(args, i, "No definition found for '" + args[i] + "'. Possible commands are: " + CommandsHelper.toString(curCommandSet) + ".");
            }
        }

        // todo optional-flag
        if (!curCommandSet.isEmpty()) {
            throw new CommandSyntaxException(args, this.lastProcessedIndex + 1, "Insufficient number of commands. Next command is one of: " + CommandsHelper.toString(curCommandSet));
        }

    }
}
