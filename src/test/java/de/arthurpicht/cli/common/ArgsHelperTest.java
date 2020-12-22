package de.arthurpicht.cli.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArgsHelperTest {

    @Test
    @DisplayName("Standard")
    void getArgsStringStd() {
        Arguments arguments = new Arguments(new String[]{"AA", "BB", "CC"});
        String argsString = arguments.asString();
        assertEquals("AA BB CC", argsString);
    }

    @Test
    @DisplayName("Single Argument")
    void getArgsStringSingle() {
        Arguments arguments = new Arguments(new String[]{"AA"});
        String argsString = arguments.asString();
        assertEquals("AA", argsString);
    }

    @Test
    @DisplayName("No Arguments")
    void getArgsStringNo() {
        Arguments arguments = new Arguments(new String[]{});
        String argsString = arguments.asString();
        assertEquals("", argsString);
    }

}