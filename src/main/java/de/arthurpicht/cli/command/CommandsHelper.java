package de.arthurpicht.cli.command;

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

    public static String getCommandChain(Command leaveCommand) {
        if (!leaveCommand.getNext().isEmpty()) throw new IllegalArgumentException("Passed leaveCommand is not leave.");

        List<String> commandChainList = new ArrayList<>();
        commandChainList.add(leaveCommand.toString());
        while(leaveCommand.hasPrevious()) {
            leaveCommand = leaveCommand.getPrevious();
            commandChainList.add(leaveCommand.toString());
        }

        Collections.reverse(commandChainList);

        StringBuilder stringBuilder = new StringBuilder();
        for (String string : commandChainList) {
            if (stringBuilder.length() > 0) stringBuilder.append(" ");
            stringBuilder.append(string);
        }

        return stringBuilder.toString();

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
