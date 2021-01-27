package de.arthurpicht.cli.command.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OneCommandTest {

    @Test
    public void test() {
        OneCommand oneCommand = new OneCommand("Test");
        assertEquals("Test", oneCommand.getCommandString());
        assertFalse(oneCommand.isOpen());
        assertTrue(oneCommand.matches("Test"));
        assertFalse(oneCommand.matches("Test2"));
    }

}