package de.arthurpicht.cli.command;

import de.arthurpicht.cli.command.tree.OneCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OneCommandTest {

    @Test
    void testToString() {
        OneCommand oneCommand = new OneCommand("command1");
        String string = oneCommand.toString();
        assertEquals("command1", string);
    }
}