package de.arthurpicht.cli.parameter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ParameterParserOneTest {

    @Test
    void parseFirst() throws ParameterParserException {

        String[] args = {"A", "B", "C", "D"};

        ParameterParserOne argumentParserOne = new ParameterParserOne();
        argumentParserOne.parse(args, 0);

        assertEquals("A", argumentParserOne.getParameterList().get(0));
        assertEquals(0, argumentParserOne.getLastProcessedIndex());
    }

    @Test
    void parseLast() throws ParameterParserException {

        String[] args = {"A", "B", "C", "D"};

        ParameterParserOne argumentParserOne = new ParameterParserOne();
        argumentParserOne.parse(args, 3);

        assertEquals("D", argumentParserOne.getParameterList().get(0));
        assertEquals(3, argumentParserOne.getLastProcessedIndex());
    }

    @Test
    void parseOutOfBounds_neg() {

        String[] args = {"A", "B", "C", "D"};

        ParameterParserOne argumentParserOne = new ParameterParserOne();
        try {
            argumentParserOne.parse(args, 4);
            fail();
        } catch (ParameterParserException e) {
            // din
        }
    }

}