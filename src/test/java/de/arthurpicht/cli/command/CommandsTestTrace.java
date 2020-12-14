package de.arthurpicht.cli.command;

import de.arthurpicht.utils.core.collection.Sets;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandsTestTrace {

    private static boolean OUT = false;

    @Test
    void traceSimple() {

        Commands commands = new Commands().add("A")
                .root().trace("A");

        assertEquals(1, commands.getCurrentCommands().size());

        Command command = commands.getCurrentCommand();
        assertEquals("A", command.asString());
    }

    @Test
    void trace2() {

        Commands commands = new Commands().add("A").add("B").add("C");

        commands = commands.root().trace("A");
        assertEquals(1, commands.getCurrentCommands().size());
        Command command = commands.getCurrentCommand();
        assertEquals("A", command.asString());

        commands = commands.trace("B");
        assertEquals(1, commands.getCurrentCommands().size());
        command = commands.getCurrentCommand();
        assertEquals("B", command.asString());

        commands = commands.trace("C");
        assertEquals(1, commands.getCurrentCommands().size());
        command = commands.getCurrentCommand();
        assertEquals("C", command.asString());
    }

    @Test
    void trace3() {

        Commands commands = new Commands().add("A").add("B").add("C")
                .root().trace("A").add("B1").add("C1")
                .root().trace("A").trace("B1").add("C2");

        Set<String> commandChains = CommandsHelper.getAllCommandChains(commands);

        if (OUT) {
            for (String commandChain : commandChains) {
                System.out.println(commandChain);
            }
        }

        Set<String> commandChainsExp = Sets.newHashSet(
                "A B C",
                "A B1 C1",
                "A B1 C2"
        );

        assertEquals(commandChainsExp, commandChains);
    }

    @Test
    void tracePath() {

        Commands commands = new Commands().add("A").add("B").add("C")
                .root().trace("A").add("B1").add("C1")
                .root().trace("A").trace("B1").add("C2");

        commands = commands.tracePath("A", "B1", "C1");
        Command command = commands.getCurrentCommand();

        assertEquals("C1", command.asString());
    }

}
