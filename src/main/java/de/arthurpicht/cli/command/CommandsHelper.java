package de.arthurpicht.cli.command;

import de.arthurpicht.utils.core.strings.Strings;

import java.util.*;

public class CommandsHelper {

    public static void debugOut(Commands commands) {

        Set<Command> rootCommandSet = commands.getRootCommands();
        Set<Command> leaveCommandSet = getLeaves(rootCommandSet);


    }

    public static Set<String> getAllCommands(Set<Command> commandSet) {
        Set<String> allCommandsSet = new HashSet<>();

        for (Command command : commandSet) {
            allCommandsSet.addAll(command.getCommands());
        }

        return allCommandsSet;
    }

    public static Set<Command> getLeaves(Set<Command> rootCommandSet) {

        Stack<Command> commandStack = new Stack<>();
        for (Command rootCommand : rootCommandSet) {
            commandStack.push(rootCommand);
        }

        Set<Command> commandLeaves = new HashSet<>();
        while (!commandStack.empty()) {
            Command curCommand = commandStack.pop();

            Set<Command> nextCommands = curCommand.getNext();

            if (nextCommands.isEmpty()) {
                commandLeaves.add(curCommand);
                continue;
            }

            for (Command nextCommand : nextCommands) {
                commandStack.push(nextCommand);
            }
        }

        return commandLeaves;
    }

}
