package de.arthurpicht.cli.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandsTestTracePath {

    @Test
    void tracePath() {

        Commands commands = new Commands().add("A").add("B").add("C")
                .reset().traceNext("A").add("B1").add("C1")
                .reset().traceNext("A").traceNext("B1").add("C2");

        commands = commands.tracePath("A", "B1", "C1");
        Command command = commands.getCurrentCommand();

        assertEquals("C1", command.asString());
    }

}
