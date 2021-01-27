package de.arthurpicht.cli.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandSequenceIteratorTest {

    @Test
    public void oneCommand() {

        CommandSequence commandSequence = new CommandSequenceBuilder().addCommands("A").build();
        CommandSequenceIterator commandSequenceIterator = new CommandSequenceIterator(commandSequence);

        assertTrue(commandSequenceIterator.hasNext());
        assertEquals("A", commandSequenceIterator.getNext().asString());

        assertFalse(commandSequenceIterator.hasNext());
    }

    @Test
    public void multipleCommands() {

        CommandSequence commandSequence = new CommandSequenceBuilder().addCommands("A", "B", "C").build();
        CommandSequenceIterator commandSequenceIterator = new CommandSequenceIterator(commandSequence);

        assertTrue(commandSequenceIterator.hasNext());
        assertEquals("A", commandSequenceIterator.getNext().asString());

        assertTrue(commandSequenceIterator.hasNext());
        assertEquals("B", commandSequenceIterator.getNext().asString());

        assertTrue(commandSequenceIterator.hasNext());
        assertEquals("C", commandSequenceIterator.getNext().asString());

        assertFalse(commandSequenceIterator.hasNext());
    }

    @Test
    public void oneCommandNoNext() {

        CommandSequence commandSequence = new CommandSequenceBuilder().addCommands("A").build();
        CommandSequenceIterator commandSequenceIterator = new CommandSequenceIterator(commandSequence, 0);

        assertFalse(commandSequenceIterator.hasNext());
    }

    @Test
    public void multipleCommandsInitForElementZero() {

        CommandSequence commandSequence = new CommandSequenceBuilder().addCommands("A", "B", "C").build();
        CommandSequenceIterator commandSequenceIterator = new CommandSequenceIterator(commandSequence, 0);

        assertTrue(commandSequenceIterator.hasNext());
        assertEquals("B", commandSequenceIterator.getNext().asString());

        assertTrue(commandSequenceIterator.hasNext());
        assertEquals("C", commandSequenceIterator.getNext().asString());

        assertFalse(commandSequenceIterator.hasNext());
    }

    @Test
    public void multipleCommandsInitForElementTwo() {

        CommandSequence commandSequence = new CommandSequenceBuilder().addCommands("A", "B", "C").build();
        CommandSequenceIterator commandSequenceIterator = new CommandSequenceIterator(commandSequence, 2);

        assertFalse(commandSequenceIterator.hasNext());
    }

    @Test
    void outOfBounds_neg() {

        CommandSequence commandSequence = new CommandSequenceBuilder().addCommands("A", "B", "C").build();
        try {
            new CommandSequenceIterator(commandSequence, 3);
            fail(IllegalArgumentException.class.getSimpleName() + " expected");
        } catch (IllegalArgumentException e) {
            // din
        }
    }

}