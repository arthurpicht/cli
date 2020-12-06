package de.arthurpicht.cli.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgsHelperTest {

    @Test
    @DisplayName("Standard")
    void getArgsStringStd() {
        String[] args = {"AA", "BB", "CC"};
        String argsString = ArgsHelper.getArgsString(args);
        assertEquals("AA BB CC", argsString);
    }

    @Test
    @DisplayName("Single Argument")
    void getArgsStringSingle() {
        String[] args = {"AA"};
        String argsString = ArgsHelper.getArgsString(args);
        assertEquals("AA", argsString);
    }

    @Test
    @DisplayName("No Arguments")
    void getArgsStringNo() {
        String[] args = {};
        String argsString = ArgsHelper.getArgsString(args);
        assertEquals("", argsString);
    }


}