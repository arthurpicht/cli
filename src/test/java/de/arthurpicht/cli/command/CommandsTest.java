package de.arthurpicht.cli.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CommandsTest {

    @Test
    void getRootCommands() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");

        Set<Command> rootCommandSet = commands.getRootCommands();
        assertEquals(1, rootCommandSet.size());

        for (Command command : rootCommandSet) {
            assertTrue(command instanceof OneCommand);
            assertEquals("A", command.getCommands().get(0));
        }

    }

    @Test
    @DisplayName("Commands2")
    void getRootCommands2() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");
        commands.root().add("D").add("E").add("F");

        Set<Command> rootCommandSet = commands.getRootCommands();
        assertEquals(2, rootCommandSet.size());

        Set<Command> rootCommandSetExp = new HashSet<>();
        rootCommandSetExp.add(new OneCommand(null, "A"));
        rootCommandSetExp.add(new OneCommand(null, "D"));

        for (Command command : rootCommandSet) {
            assertTrue(command instanceof OneCommand);
//            assertEquals("A", command.getCommands().get(0));
        }

        assertEquals(rootCommandSetExp, rootCommandSet);

    }



}