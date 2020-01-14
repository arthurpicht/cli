package de.arthurpicht.cli.parameter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParameterParserManyTest {

    @Test
    void parseFirst() {

        ParameterParserMany argumentParserMany = new ParameterParserMany(1);

        String[] args = {"A", "B", "C", "D"};

        try {
            argumentParserMany.parse(args, 0);

            assertEquals(1, argumentParserMany.getParameterList().size());
            assertEquals("A", argumentParserMany.getParameterList().get(0));
            assertEquals(0, argumentParserMany.getLastProcessedIndex());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parseFirstTwo() {

        ParameterParserMany argumentParserMany = new ParameterParserMany(2);

        String[] args = {"A", "B", "C", "D"};
        try {
            argumentParserMany.parse(args, 0);
            assertEquals(2, argumentParserMany.getParameterList().size());
            assertEquals("A", argumentParserMany.getParameterList().get(0));
            assertEquals("B", argumentParserMany.getParameterList().get(1));
            assertEquals(1, argumentParserMany.getLastProcessedIndex());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parseLastThree() {

        ParameterParserMany argumentParserMany = new ParameterParserMany(3);

        String[] args = {"A", "B", "C", "D"};
        try {
            argumentParserMany.parse(args, 1);
            assertEquals(3, argumentParserMany.getParameterList().size());
            assertEquals("B", argumentParserMany.getParameterList().get(0));
            assertEquals("C", argumentParserMany.getParameterList().get(1));
            assertEquals("D", argumentParserMany.getParameterList().get(2));
            assertEquals(3, argumentParserMany.getLastProcessedIndex());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parseLastFourOutOfBounds() {

        ParameterParserMany argumentParserMany = new ParameterParserMany(4);

        String[] args = {"A", "B", "C", "D"};
        try {
            argumentParserMany.parse(args, 1);
            fail();

        } catch (ParameterParserException e) {
            System.out.println("Expected Exception: " + e.getMessage());
        }
    }

    @Test
    void parseOutOfBounds() {

        ParameterParserMany argumentParserMany = new ParameterParserMany(1);

        String[] args = {"A", "B", "C", "D"};
        try {
            argumentParserMany.parse(args, 5);
            fail();

        } catch (ParameterParserException e) {
            System.out.println("Expected Exception: " + e.getMessage());
        }
    }


}