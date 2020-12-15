package de.arthurpicht.cli.command;

import de.arthurpicht.cli.common.CLISpecificationException;
import de.arthurpicht.utils.core.collection.Sets;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CommandsTestTraceNext {

    private static boolean OUT = true;

    @Test
    void empty_neg() {
        Commands commands = new Commands();
        try {
            commands.traceNext("some");
            fail();
        } catch (CLISpecificationException exception) {
            out("Expected exception message: " +
                    exception.getMessage());
        }
    }

    @Test
    void ambiguous_neg() {
        Commands commands = new Commands().addOneOf("A", "B", "C");
        try {
            commands.traceNext("some");
            fail();
        } catch (CLISpecificationException exception) {
            out("Expected exception message: " +
                    exception.getMessage());
        }
    }

    @Test
    void noSuchCommand_neg() {
        Commands commands = new Commands().add("A");
        try {
            commands.traceNext("some");
            fail();
        } catch (CLISpecificationException exception) {
            out("Expected exception message: " +
                    exception.getMessage());
        }
    }

    @Test
    void traceSimple() {

        Commands commands = new Commands().add("A")
                .reset().traceNext("A");

        assertEquals(1, commands.getCurrentCommands().size());

        Command command = commands.getCurrentCommand();
        assertEquals("A", command.asString());
    }

    @Test
    void traceInOneLine() {

        Commands commands = new Commands()
                .add("A").add("B").add("C")
                .reset().traceNext("A");

        assertEquals(1, commands.getCurrentCommands().size());
        Command command = commands.getCurrentCommand();
        assertEquals("A", command.asString());

        commands = commands.traceNext("B");
        assertEquals(1, commands.getCurrentCommands().size());
        command = commands.getCurrentCommand();
        assertEquals("B", command.asString());

        commands = commands.traceNext("C");
        assertEquals(1, commands.getCurrentCommands().size());
        command = commands.getCurrentCommand();
        assertEquals("C", command.asString());
    }

    @Test
    void traceSingleRoot() {

        Commands commands = new Commands().add("A").add("B").add("C")
                .reset().traceNext("A").add("B1").add("C1")
                .reset().traceNext("A").traceNext("B1").add("C2");

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

    private static void out(String string) {
        if (OUT) System.out.println(string);
    }


}
