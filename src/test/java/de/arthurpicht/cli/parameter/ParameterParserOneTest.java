package de.arthurpicht.cli.parameter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ParameterParserOneTest {

    @Test
    void parseFirst() {

        ParameterParserOne argumentParserOne = new ParameterParserOne();

        String[] args = {"A", "B", "C", "D"};
        try {
            argumentParserOne.parse(args, 0);
            assertEquals("A", argumentParserOne.getParameterList().get(0));
            assertEquals(0, argumentParserOne.getLastProcessedIndex());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parseLast() {

        ParameterParserOne argumentParserOne = new ParameterParserOne();

        String[] args = {"A", "B", "C", "D"};
        try {
            argumentParserOne.parse(args, 3);
            assertEquals("D", argumentParserOne.getParameterList().get(0));
            assertEquals(3, argumentParserOne.getLastProcessedIndex());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parseOutOfBounds() {

        ParameterParserOne argumentParserOne = new ParameterParserOne();

        String[] args = {"A", "B", "C", "D"};
        try {
            argumentParserOne.parse(args, 4);
            fail();

        } catch (ParameterParserException e) {
            System.out.println("Expected Exception: " + e.getMessage());
        }
    }


}