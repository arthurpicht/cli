package de.arthurpicht.cli.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentIteratorTest {

    @Test
    public void empty() {
        ArgumentIterator argumentIterator = new ArgumentIterator(new Arguments(new String[]{}));
        assertFalse(argumentIterator.hasNext());
    }

    @Test
    public void empty_neg() {
        ArgumentIterator argumentIterator = new ArgumentIterator(new Arguments(new String[]{}));

        try {
            argumentIterator.getNext();
        } catch (IllegalStateException e) {
            // din
        }
    }

    @Test
    public void oneArgument() {
        ArgumentIterator argumentIterator = new ArgumentIterator(new Arguments(new String[]{"A"}));

        assertTrue(argumentIterator.hasNext());
        assertEquals("A", argumentIterator.getNext());
        assertFalse(argumentIterator.hasNext());
    }

    @Test
    public void twoArguments() {
        ArgumentIterator argumentIterator = new ArgumentIterator(new Arguments(new String[]{"A", "B"}));

        assertTrue(argumentIterator.hasNext());
        assertEquals("A", argumentIterator.getNext());
        assertTrue(argumentIterator.hasNext());
        assertEquals("B", argumentIterator.getNext());
        assertFalse(argumentIterator.hasNext());
    }

    @Test
    public void constructorWithInitialIndex() {
        ArgumentIterator argumentIterator = new ArgumentIterator(new String[]{"A", "B"}, 0);

        assertEquals(0, argumentIterator.getIndex());
        assertTrue(argumentIterator.hasNext());
        assertFalse(argumentIterator.hasPrevious());
    }

    @Test
    public void constructorWithInitialIndexTooSmall_neg() {
        try {
            new ArgumentIterator(new String[]{"A", "B"}, -2);
            fail("Exception excepted");
        } catch (IllegalArgumentException e) {
            // din
        }
    }

    @Test
    public void constructorWithInitialIndexTooBig_neg() {
        try {
            new ArgumentIterator(new String[]{"A", "B"}, 2);
            fail("Exception excepted");
        } catch (IllegalArgumentException e) {
            // din
        }
    }


}