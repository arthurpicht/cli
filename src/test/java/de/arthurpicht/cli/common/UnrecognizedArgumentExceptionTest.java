package de.arthurpicht.cli.common;

import org.junit.jupiter.api.Test;

import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.*;

class UnrecognizedArgumentExceptionTest {

    @Test
    void getArgumentPointerStringMultiAt0() {

        String[] args = {"AA", "BB", "CC"};
        Arguments arguments = new Arguments(args);
        UnrecognizedArgumentException unrecognizedArgumentException = new UnrecognizedArgumentException("test", arguments, 0, "myMessage");

        assertEquals("test", unrecognizedArgumentException.getExecutableName());

        assertEquals(0, unrecognizedArgumentException.getArgumentIndex());
        assertEquals("myMessage", unrecognizedArgumentException.getMessage());

        println(unrecognizedArgumentException.getArgsAsString());
        println(unrecognizedArgumentException.getArgumentPointerString());

        assertEquals("^", unrecognizedArgumentException.getArgumentPointerString());

        println(unrecognizedArgumentException.getCallString());
        println(unrecognizedArgumentException.getCallPointerString());

        assertEquals("     ^", unrecognizedArgumentException.getCallPointerString());
    }

    @Test
    void getArgumentPointerStringMultiAt1() {

        String[] args = {"AA", "BB", "CC"};
        Arguments arguments = new Arguments(args);
        UnrecognizedArgumentException unrecognizedArgumentException = new UnrecognizedArgumentException("test", arguments, 1, "myMessage");

        assertEquals(1, unrecognizedArgumentException.getArgumentIndex());
        assertEquals("myMessage", unrecognizedArgumentException.getMessage());

        println(unrecognizedArgumentException.getArgsAsString());
        println(unrecognizedArgumentException.getArgumentPointerString());

        assertEquals("   ^", unrecognizedArgumentException.getArgumentPointerString());

        println(unrecognizedArgumentException.getCallString());
        println(unrecognizedArgumentException.getCallPointerString());

        assertEquals("        ^", unrecognizedArgumentException.getCallPointerString());
    }

    @Test
    void getArgumentPointerStringMultiAtLast() {

        String[] args = {"AA", "BB", "CC"};
        Arguments arguments = new Arguments(args);
        UnrecognizedArgumentException unrecognizedArgumentException = new UnrecognizedArgumentException("test", arguments, 2, "myMessage");

        assertEquals(2, unrecognizedArgumentException.getArgumentIndex());
        assertEquals("myMessage", unrecognizedArgumentException.getMessage());

        println(unrecognizedArgumentException.getArgsAsString());
        println(unrecognizedArgumentException.getArgumentPointerString());

        assertEquals("      ^", unrecognizedArgumentException.getArgumentPointerString());

        println(unrecognizedArgumentException.getCallString());
        println(unrecognizedArgumentException.getCallPointerString());

        assertEquals("           ^", unrecognizedArgumentException.getCallPointerString());
    }

    @Test
    void getArgumentPointerStringMultiAtLastPlus1() {

        String[] args = {"AA", "BB", "CC"};
        Arguments arguments = new Arguments(args);
        UnrecognizedArgumentException unrecognizedArgumentException = new UnrecognizedArgumentException("test", arguments, 3, "myMessage");

        println(unrecognizedArgumentException.getArgsAsString());
        println(unrecognizedArgumentException.getArgumentPointerString());

        assertEquals("         ^", unrecognizedArgumentException.getArgumentPointerString());

        println(unrecognizedArgumentException.getCallString());
        println(unrecognizedArgumentException.getCallPointerString());

        assertEquals("              ^", unrecognizedArgumentException.getCallPointerString());
    }

    @Test
    void getArgumentPointerStringForZeroArgument() {

        String[] args = {};
        Arguments arguments = new Arguments(args);
        UnrecognizedArgumentException unrecognizedArgumentException = new UnrecognizedArgumentException("test", arguments, 0, "myMessage");

        println(unrecognizedArgumentException.getArgsAsString());
        println(unrecognizedArgumentException.getArgumentPointerString());

        assertEquals("^", unrecognizedArgumentException.getArgumentPointerString());

        println(unrecognizedArgumentException.getCallString());
        println(unrecognizedArgumentException.getCallPointerString());

        assertEquals("     ^", unrecognizedArgumentException.getCallPointerString());
    }

}