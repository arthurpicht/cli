package de.arthurpicht.cli.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandsTestTrace {

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


}
