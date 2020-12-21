package de.arthurpicht.cli.common;

import de.arthurpicht.utils.core.collection.Lists;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentsTest {

    @Test
    public void empty() {
        Arguments arguments = new Arguments(new String[]{});

        assertTrue(arguments.isEmpty());
        assertEquals(0, arguments.size());
        assertEquals("", arguments.asString());
        assertTrue(arguments.asList().isEmpty());
    }

    @Test
    public void oneElement() {
        Arguments arguments = new Arguments(new String[]{"A"});

        assertFalse(arguments.isEmpty());
        assertEquals(1, arguments.size());
        assertEquals("A", arguments.asString());
        assertEquals("A", arguments.get(0));
        assertEquals(arguments.asList(), Lists.newArrayList("A"));
    }

    @Test
    public void twoElements() {
        Arguments arguments = new Arguments(new String[]{"A", "B"});

        assertFalse(arguments.isEmpty());
        assertEquals(2, arguments.size());
        assertEquals("A B", arguments.asString());
        assertEquals("A", arguments.get(0));
        assertEquals("B", arguments.get(1));
        assertEquals(arguments.asList(), Lists.newArrayList("A", "B"));
    }

    @Test
    public void outOfBounds_neg() {
        Arguments arguments = new Arguments(new String[]{"A"});

        try {
            arguments.get(1);
            fail();
        } catch (IllegalStateException e) {
            // din
        }
    }

}