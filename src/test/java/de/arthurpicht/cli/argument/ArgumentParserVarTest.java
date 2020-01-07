package de.arthurpicht.cli.argument;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentParserVarTest {

    @Test
    void parseFirst() {

        ArgumentParserVar argumentParserVar = new ArgumentParserVar(0);

        String[] args = {"A", "B", "C", "D"};

        try {
            argumentParserVar.parse(args, 0);

            assertEquals(4, argumentParserVar.getArgumentList().size());
            assertEquals("A", argumentParserVar.getArgumentList().get(0));
            assertEquals("B", argumentParserVar.getArgumentList().get(1));
            assertEquals("C", argumentParserVar.getArgumentList().get(2));
            assertEquals("D", argumentParserVar.getArgumentList().get(3));

            assertEquals(3, argumentParserVar.getLastProcessedIndex());

        } catch (ArgumentParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parseLast() {

        ArgumentParserVar argumentParserVar = new ArgumentParserVar(0);

        String[] args = {"A", "B", "C", "D"};

        try {
            argumentParserVar.parse(args, 3);

            assertEquals(1, argumentParserVar.getArgumentList().size());
            assertEquals("D", argumentParserVar.getArgumentList().get(0));

            assertEquals(3, argumentParserVar.getLastProcessedIndex());

        } catch (ArgumentParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parseMidMin2() {

        ArgumentParserVar argumentParserVar = new ArgumentParserVar(2);

        String[] args = {"A", "B", "C", "D"};

        try {
            argumentParserVar.parse(args, 2);

            assertEquals(2, argumentParserVar.getArgumentList().size());
            assertEquals("C", argumentParserVar.getArgumentList().get(0));
            assertEquals("D", argumentParserVar.getArgumentList().get(1));
            assertEquals(3, argumentParserVar.getLastProcessedIndex());

        } catch (ArgumentParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parseMidMin3Fail() {

        ArgumentParserVar argumentParserVar = new ArgumentParserVar(3);

        String[] args = {"A", "B", "C", "D"};

        try {
            argumentParserVar.parse(args, 2);
            fail();

        } catch (ArgumentParserException e) {
            System.out.println("Expected Exception: " + e.getMessage());
        }
    }



}