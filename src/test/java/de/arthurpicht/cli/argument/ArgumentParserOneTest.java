package de.arthurpicht.cli.argument;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ArgumentParserOneTest {

    @Test
    void parseFirst() {

        ArgumentParserOne argumentParserOne = new ArgumentParserOne();

        String[] args = {"A", "B", "C", "D"};
        try {
            argumentParserOne.parse(args, 0);
            assertEquals("A", argumentParserOne.getArgumentList().get(0));
            assertEquals(0, argumentParserOne.getLastProcessedIndex());

        } catch (ArgumentParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parseLast() {

        ArgumentParserOne argumentParserOne = new ArgumentParserOne();

        String[] args = {"A", "B", "C", "D"};
        try {
            argumentParserOne.parse(args, 3);
            assertEquals("D", argumentParserOne.getArgumentList().get(0));
            assertEquals(3, argumentParserOne.getLastProcessedIndex());

        } catch (ArgumentParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parseOutOfBounds() {

        ArgumentParserOne argumentParserOne = new ArgumentParserOne();

        String[] args = {"A", "B", "C", "D"};
        try {
            argumentParserOne.parse(args, 4);
            fail();

        } catch (ArgumentParserException e) {
            System.out.println("Expected Exception: " + e.getMessage());
        }
    }


}