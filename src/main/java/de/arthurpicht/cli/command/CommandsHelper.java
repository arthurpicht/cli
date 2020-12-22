package de.arthurpicht.cli.command;

import de.arthurpicht.utils.core.strings.Strings;

import java.util.*;

public class CommandsHelper {

    public static Set<String> getAllCommands(Set<Command> commandSet) {
        Set<String> allCommandsSet = new HashSet<>();

        for (Command command : commandSet) {
            allCommandsSet.addAll(command.getCommandStrings());
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

    public static Set<String> getAllCommandChains(Commands commands) {
        Set<Command> leaveCommands = getLeaves(commands.getRootCommands());
        Set<String> commandChains = new HashSet<>();

        for (Command leaveCommand : leaveCommands) {
            commandChains.add(leaveCommand.getCommandChainString());
        }

        return commandChains;
    }

    public static String toFormattedList(Set<Command> commandSet) {

        Set<String> commandStringSet = new HashSet<>();
        for (Command command : commandSet) {
            commandStringSet.addAll(command.getCommandStrings());
        }

        List<String> commandStringList = new ArrayList<>(commandStringSet);
        Collections.sort(commandStringList);

        return Strings.listing(commandStringList, " | ", "[ ", " ]");
    }

    public static boolean hasOpenLeaves(Commands commands) {
        Set<Command> leaveCommands = getLeaves(commands.getRootCommands());
        for (Command command : leaveCommands) {
            if (command.isOpen()) return true;
        }
        return false;
    }

}
