package de.arthurpicht.cli.command;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.command.tree.Command;
import de.arthurpicht.cli.command.tree.CommandTerminator;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.ArrayList;
import java.util.List;

public class CommandSequence {

    private final List<Command> commandList;

    public CommandSequence(List<Command> commandList, Options specificOptions, Parameters parameters, CommandExecutor commandExecutor) {
        if (commandList.isEmpty()) throw new RuntimeException("Specified command list must not be empty.");
        this.commandList = commandList;
        Command lastCommand = commandList.get(commandList.size() - 1);
        CommandTerminator commandTerminator = new CommandTerminator(specificOptions, parameters, commandExecutor);
        lastCommand.terminate(commandTerminator);
    }

    public List<Command> getCommandList() {
        return this.commandList;
    }

    public List<Command> getCommandListWithoutRoot() {
        return this.commandList.subList(1, this.commandList.size());
    }

    public Command getFirstCommand() {
        if (commandList.isEmpty()) throw new RuntimeException("Command list must not be empty.");
        return this.commandList.get(0);
    }

    public Command getLastCommand() {
        if (commandList.isEmpty()) throw new RuntimeException("Command list must not be empty.");
        return this.commandList.get(this.commandList.size() - 1);
    }

    public String asString() {
        List<String> commandStringList = new ArrayList<>();
        for (Command command : this.commandList) {
            commandStringList.add(command.toString());
        }
        return Strings.listing(commandStringList, " ");
    }

    public CommandTerminator getCommandTerminator() {
        return getLastCommand().getCommandTerminator();
    }

}
