package de.arthurpicht.cli.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnrecognizedCLArgumentExceptionTest {

    @Test
    void getArgumentPointerStringMultiAt0() {

        String[] args = {"AA", "BB", "CC"};
        UnrecognizedCLArgumentException unrecognizedCLArgumentException = new UnrecognizedCLArgumentException(args, 0, "myMessage");

        assertEquals(0, unrecognizedCLArgumentException.getArgumentIndex());
        assertEquals("myMessage", unrecognizedCLArgumentException.getMessage());

        System.out.println(CLIArgsHelper.getArgsString(args));
        System.out.println(unrecognizedCLArgumentException.getArgumentPointerString());

        assertEquals("^", unrecognizedCLArgumentException.getArgumentPointerString());
    }

    @Test
    void getArgumentPointerStringMultiAt1() {

        String[] args = {"AA", "BB", "CC"};
        UnrecognizedCLArgumentException unrecognizedCLArgumentException = new UnrecognizedCLArgumentException(args, 1, "myMessage");

        assertEquals(1, unrecognizedCLArgumentException.getArgumentIndex());
        assertEquals("myMessage", unrecognizedCLArgumentException.getMessage());

        System.out.println(CLIArgsHelper.getArgsString(args));
        System.out.println(unrecognizedCLArgumentException.getArgumentPointerString());

        assertEquals("   ^", unrecognizedCLArgumentException.getArgumentPointerString());
    }

    // TODO Verhalten bei fehlendem Argument




}