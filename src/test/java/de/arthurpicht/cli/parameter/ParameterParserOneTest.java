package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.common.ArgumentIterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ParameterParserOneTest {

    @Test
    void parseFirst() throws ParameterParserException {

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        ParameterParserOne argumentParserOne = new ParameterParserOne();
        argumentParserOne.parse(argumentIterator);

        assertEquals("A", argumentParserOne.getParameterList().get(0));
        assertEquals(0, argumentIterator.getIndex());
    }

    @Test
    void parseLast() throws ParameterParserException {

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 2);

        ParameterParserOne argumentParserOne = new ParameterParserOne();
        argumentParserOne.parse(argumentIterator);

        assertEquals("D", argumentParserOne.getParameterList().get(0));
        assertEquals(3, argumentIterator.getIndex());
    }

    @Test
    void parseOutOfBounds_neg() {

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 3);

        ParameterParserOne argumentParserOne = new ParameterParserOne();
        try {
            argumentParserOne.parse(argumentIterator);
            fail("Exception expected.");
        } catch (ParameterParserException e) {
            // din
        }
    }

}