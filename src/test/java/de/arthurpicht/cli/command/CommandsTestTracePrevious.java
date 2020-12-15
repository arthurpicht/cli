package de.arthurpicht.cli.command;

import de.arthurpicht.cli.common.CLISpecificationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CommandsTestTracePrevious {

    private static final boolean OUT = true;

    @Test
    public void empty_neg() {
        Commands commands = new Commands();
        try {
            commands.tracePrevious();
            fail();
        } catch (CLISpecificationException exception) {
            out("Expected exception message: " +
                     exception.getMessage());
        }
    }

    @Test
    public void rootCommand_neg() {
        Commands commands = new Commands().add("A");
        try {
            commands.tracePrevious();
            fail();
        } catch (CLISpecificationException exception) {
            out("Expected exception message: " +
                    exception.getMessage());
        }
    }

    @Test
    public void afterReset_neg() {
        Commands commands = new Commands().add("A").add("B").reset();
        try {
            commands.tracePrevious();
            fail();
        } catch (CLISpecificationException exception) {
            out("Expected exception message: " +
                    exception.getMessage());
        }
    }

    @Test
    public void ambiguousCommand_neg() {
        Commands commands = new Commands().add("A").addOneOf("B1", "B2", "B3");
        try {
            commands.tracePrevious();
            fail();
        } catch (CLISpecificationException exception) {
            out("Expected exception message: " +
                    exception.getMessage());
        }
    }

    @Test
    public void tracePrevious() {
        Commands commands = new Commands().add("A").add("B").tracePrevious();
        assertEquals("A", commands.getCurrentCommand().asString());
    }

    private static void out(String string) {
        if (OUT) System.out.println(string);
    }


}
