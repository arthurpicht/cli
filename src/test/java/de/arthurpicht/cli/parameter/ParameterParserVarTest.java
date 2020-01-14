package de.arthurpicht.cli.parameter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParameterParserVarTest {

    @Test
    void parseFirst() {

        ParameterParserVar argumentParserVar = new ParameterParserVar(0);

        String[] args = {"A", "B", "C", "D"};

        try {
            argumentParserVar.parse(args, 0);

            assertEquals(4, argumentParserVar.getParameterList().size());
            assertEquals("A", argumentParserVar.getParameterList().get(0));
            assertEquals("B", argumentParserVar.getParameterList().get(1));
            assertEquals("C", argumentParserVar.getParameterList().get(2));
            assertEquals("D", argumentParserVar.getParameterList().get(3));

            assertEquals(3, argumentParserVar.getLastProcessedIndex());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parseLast() {

        ParameterParserVar argumentParserVar = new ParameterParserVar(0);

        String[] args = {"A", "B", "C", "D"};

        try {
            argumentParserVar.parse(args, 3);

            assertEquals(1, argumentParserVar.getParameterList().size());
            assertEquals("D", argumentParserVar.getParameterList().get(0));

            assertEquals(3, argumentParserVar.getLastProcessedIndex());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parseMidMin2() {

        ParameterParserVar argumentParserVar = new ParameterParserVar(2);

        String[] args = {"A", "B", "C", "D"};

        try {
            argumentParserVar.parse(args, 2);

            assertEquals(2, argumentParserVar.getParameterList().size());
            assertEquals("C", argumentParserVar.getParameterList().get(0));
            assertEquals("D", argumentParserVar.getParameterList().get(1));
            assertEquals(3, argumentParserVar.getLastProcessedIndex());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parseMidMin3Fail() {

        ParameterParserVar argumentParserVar = new ParameterParserVar(3);

        String[] args = {"A", "B", "C", "D"};

        try {
            argumentParserVar.parse(args, 2);
            fail();

        } catch (ParameterParserException e) {
            System.out.println("Expected Exception: " + e.getMessage());
        }
    }



}