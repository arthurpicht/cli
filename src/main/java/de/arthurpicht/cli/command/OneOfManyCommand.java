package de.arthurpicht.cli.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OneOfManyCommand extends Command {

    private List<String> commandList;

    public OneOfManyCommand(Command previousCommand, String... commands) {
        super(previousCommand);

        // TODO assert
        if (commands.length == 0) throw new CommandSpecException("command list empty");

        this.commandList = new ArrayList<String>(Arrays.asList(commands));
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public List<String> getCommands() {
        return this.commandList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OneOfManyCommand that = (OneOfManyCommand) o;
        return commandList.equals(that.commandList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandList);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ ");
        boolean first = true;
        for (String commandString : this.commandList) {
            if (!first) stringBuilder.append(" | ");
            stringBuilder.append(commandString);
            first = false;
        }
        stringBuilder.append(" ]");
        return stringBuilder.toString();
    }
}
