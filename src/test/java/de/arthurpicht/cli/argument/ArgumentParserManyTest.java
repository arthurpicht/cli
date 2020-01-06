package de.arthurpicht.cli.argument;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentParserManyTest {

    @Test
    void parseFirst() {

        ArgumentParserMany argumentParserMany = new ArgumentParserMany(1);

        String[] args = {"A", "B", "C", "D"};
        try {
            argumentParserMany.parse(args, 0);

            assertEquals(1, argumentParserMany.getArgumentList().size());
            assertEquals("A", argumentParserMany.getArgumentList().get(0));
            assertEquals(0, argumentParserMany.getLastProcessedIndex());

        } catch (ArgumentParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parseFirstTwo() {

        ArgumentParserMany argumentParserMany = new ArgumentParserMany(2);

        String[] args = {"A", "B", "C", "D"};
        try {
            argumentParserMany.parse(args, 0);
            assertEquals(2, argumentParserMany.getArgumentList().size());
            assertEquals("A", argumentParserMany.getArgumentList().get(0));
            assertEquals("B", argumentParserMany.getArgumentList().get(1));
            assertEquals(1, argumentParserMany.getLastProcessedIndex());

        } catch (ArgumentParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parseLastThree() {

        ArgumentParserMany argumentParserMany = new ArgumentParserMany(3);

        String[] args = {"A", "B", "C", "D"};
        try {
            argumentParserMany.parse(args, 1);
            assertEquals(3, argumentParserMany.getArgumentList().size());
            assertEquals("B", argumentParserMany.getArgumentList().get(0));
            assertEquals("C", argumentParserMany.getArgumentList().get(1));
            assertEquals("D", argumentParserMany.getArgumentList().get(2));
            assertEquals(3, argumentParserMany.getLastProcessedIndex());

        } catch (ArgumentParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parseLastFourOutOfBounds() {

        ArgumentParserMany argumentParserMany = new ArgumentParserMany(4);

        String[] args = {"A", "B", "C", "D"};
        try {
            argumentParserMany.parse(args, 1);
            fail();

        } catch (ArgumentParserException e) {
            System.out.println("Expected Exception: " + e.getMessage());
        }
    }

    @Test
    void parseOutOfBounds() {

        ArgumentParserMany argumentParserMany = new ArgumentParserMany(1);

        String[] args = {"A", "B", "C", "D"};
        try {
            argumentParserMany.parse(args, 5);
            fail();

        } catch (ArgumentParserException e) {
            System.out.println("Expected Exception: " + e.getMessage());
        }
    }


}