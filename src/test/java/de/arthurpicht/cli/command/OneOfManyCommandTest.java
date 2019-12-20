package de.arthurpicht.cli.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OneOfManyCommandTest {

    @Test
    void testToStringOne() {
        OneOfManyCommand oneOfManyCommand = new OneOfManyCommand(null, "command1");
        String string = oneOfManyCommand.toString();
        assertEquals("[ command1 ]", string);
    }

    @Test
    void testToStringTwo() {
        OneOfManyCommand oneOfManyCommand = new OneOfManyCommand(null, "command1", "command2");
        String string = oneOfManyCommand.toString();
        assertEquals("[ command1 | command2 ]", string);
    }

    @Test
    void testToStringThree() {
        OneOfManyCommand oneOfManyCommand = new OneOfManyCommand(null, "command1", "command2", "command3");
        String string = oneOfManyCommand.toString();
        assertEquals("[ command1 | command2 | command3 ]", string);
    }



}