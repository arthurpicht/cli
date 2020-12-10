package de.arthurpicht.cli.command;

import de.arthurpicht.utils.core.collection.Sets;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void getCommandChainString() {

        Commands commands = new Commands();
        Set<Command> leafCommands = commands.add("A").add("B").add("C").getCurrentCommands();

        assertEquals(1, leafCommands.size());

        Command commandC = Sets.getSomeElement(leafCommands);

        String commandChainString = commandC.getCommandChainString();
        assertEquals("A B C", commandChainString);

    }

    @Test
    void getCommandChainString2() {

        Commands commands = new Commands();
        Set<Command> leafCommands = commands.add("A").add("B").addOpen().getCurrentCommands();

        assertEquals(1, leafCommands.size());

        Command commandB = Sets.getSomeElement(leafCommands);

        String commandChainString = commandB.getCommandChainString();
        assertEquals("A B *", commandChainString);

    }


}