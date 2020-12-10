package de.arthurpicht.cli.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OneCommandTest {

    @Test
    void testToString() {
        OneCommand oneCommand = new OneCommand(null,"command1");
        String string = oneCommand.toString();
        assertEquals("command1", string);
    }
}