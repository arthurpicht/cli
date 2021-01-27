package de.arthurpicht.cli.command;

import de.arthurpicht.cli.command.tree.Command;
import de.arthurpicht.utils.core.collection.Sets;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.*;

class CommandsHelperTest {

    @Test
    void toFormattedList() {

        Commands commands = new Commands()
                .add(new CommandSequenceBuilder().addCommands("A").build())
                .add(new CommandSequenceBuilder().addCommands("B").build())
                .add(new CommandSequenceBuilder().addCommands("X1", "X2", "X3").build());

        Set<Command> initialCommands = commands.getCommandTree().getRootNode().getChildrenElements();

        String commandsFormatted = CommandsHelper.toFormattedList(initialCommands);

        println(commandsFormatted);

        assertEquals("[ A | B | X1 ]", commandsFormatted);
    }
}