package de.arthurpicht.cli.command;

import de.arthurpicht.cli.command.exceptions.CommandSpecException;
import de.arthurpicht.utils.core.collection.Sets;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.HashSet;
import java.util.Set;

public class CommandsPrecondition {

    public static void checkPreconditionsSpecCommand(Commands commands, Set<String> commandsToBeAdded) {
        assertLegalCharacters(commandsToBeAdded);
        assertLevelAsNonOpen(commands);
        assertForNonDuplicate(commands, commandsToBeAdded);
    }

    public static void checkPreconditionOpenCommand(Commands commands) {
        assertCommandLevelEmpty(commands);
    }

    private static void assertForNonDuplicate(Commands commands, Set<String> argSetToBeAdded) {

        Set<Command> commandList = getNextCommandsForCurrentLevel(commands);

        Set<String> allCommands = CommandsHelper.getAllCommands(commandList);
        Set<String> intersection = Sets.intersection(allCommands, argSetToBeAdded);

        if (intersection.size() > 0) {
            throw new CommandSpecException("The following commands are already defined: " + Strings.listing(intersection, ", ", "[", "]"));
        }
    }

    private static Set<Command> getNextCommandsForCurrentLevel(Commands commands) {
        if (commands.hasCurrentCommands()) {
            Set<Command> nextCommands = new HashSet<>();
            for (Command curCommand : commands.getCurrentCommands()) {
                nextCommands.addAll(curCommand.getNext());
            }
            return nextCommands;
//            return commands.getCurrentCommand().getNext();
        } else {
            return commands.getRootCommands();
        }
    }

    private static void assertCommandLevelEmpty(Commands commands) {
        if (!isCommandLevelEmpty(commands)) {
            throw new CommandSpecException("Command level is not empty.");
        }
    }

    private static boolean isCommandLevelEmpty(Commands commands) {
        Set<Command> commandSet = getNextCommandsForCurrentLevel(commands);
        return (commandSet.isEmpty());
    }

    private static void assertLevelAsNonOpen(Commands commands) {
        if (isCommandLevelOpen(commands)) {
            throw new CommandSpecException("Command level already defined as open.");
        }
    }

    private static boolean isCommandLevelOpen(Commands commands) {
        Set<Command> commandSet = getNextCommandsForCurrentLevel(commands);
        if (commandSet.size() == 1) {
            Command command = Sets.getSomeElement(commandSet);
            return command.isOpen();
        }
        return false;
    }

    private static void assertLegalCharacters(Set<String> commandStrings) {
        for (String command : commandStrings) {
            assertLegalCharacters(command);
        }
    }

    private static void assertLegalCharacters(String commandString) {
        if (commandString.isEmpty()) throw new CommandSpecException("Empty command found.");
        if (commandString.contains(" "))
            throw new CommandSpecException("No whitespace allowed. \"" + commandString + "\"");
        if (commandString.startsWith("-")) throw new CommandSpecException("Command must not start with \"-\": " + commandString);
    }

}
