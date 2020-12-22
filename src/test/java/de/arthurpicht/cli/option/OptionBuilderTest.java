package de.arthurpicht.cli.option;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionBuilderTest {

    @Test
    void shortName() {

        Option option = new OptionBuilder().withShortName('s').build("id");

        assertEquals("id", option.getId());
        assertTrue(option.hasShortName());
        assertEquals('s', option.getShortName());
        assertFalse(option.hasLongName());
        assertFalse(option.hasArgument());
        assertFalse(option.hasHelpText());
    }

    @Test
    void argument() {

        Option option = new OptionBuilder().withShortName('t').hasArgument().withArgumentName("ARG").build("id");

        assertEquals("id", option.getId());
        assertTrue(option.hasShortName());
        assertEquals('t', option.getShortName());
        assertFalse(option.hasLongName());
        assertTrue(option.hasArgument());
        assertTrue(option.hasArgumentName());
        assertEquals("ARG", option.getArgumentName());
        assertFalse(option.hasHelpText());
    }

}