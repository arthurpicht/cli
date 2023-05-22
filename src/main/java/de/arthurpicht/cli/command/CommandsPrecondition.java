package de.arthurpicht.cli.command;

import de.arthurpicht.cli.command.exceptions.CommandSpecException;

public class CommandsPrecondition {

    public static void assertLegalCharacters(String commandString) {
        if (commandString.isEmpty()) throw new CommandSpecException("Empty command found.");
        if (commandString.contains(" "))
            throw new CommandSpecException("No whitespace allowed. \"" + commandString + "\"");
        if (commandString.startsWith("-")) throw new CommandSpecException("Command must not start with \"-\": " + commandString);
    }

}
