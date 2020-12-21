package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.common.ArgumentIterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParameterParserManyTest {

    private static boolean OUT = true;

    @Test
    void parseFirst() {

        ParameterParserMany argumentParserMany = new ParameterParserMany(1);

        String[] args = {"A"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            argumentParserMany.parse(argumentIterator);

            assertEquals(1, argumentParserMany.getParameterList().size());
            assertEquals("A", argumentParserMany.getParameterList().get(0));
            assertEquals(0, argumentIterator.getIndex());

        } catch (ParameterParserException e) {
            out(e.getMessage());
            fail();
        }
    }

    @Test
    void parseFirstTwo() {

        ParameterParserMany argumentParserMany = new ParameterParserMany(2);

        String[] args = {"A", "B"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            argumentParserMany.parse(argumentIterator);
            assertEquals(2, argumentParserMany.getParameterList().size());
            assertEquals("A", argumentParserMany.getParameterList().get(0));
            assertEquals("B", argumentParserMany.getParameterList().get(1));
            assertEquals(1, argumentIterator.getIndex());

        } catch (ParameterParserException e) {
            out(e.getMessage());
            fail();
        }
    }

    @Test
    void parseLastThree() {

        ParameterParserMany argumentParserMany = new ParameterParserMany(3);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            argumentParserMany.parse(argumentIterator);
            assertEquals(3, argumentParserMany.getParameterList().size());
            assertEquals("B", argumentParserMany.getParameterList().get(0));
            assertEquals("C", argumentParserMany.getParameterList().get(1));
            assertEquals("D", argumentParserMany.getParameterList().get(2));
            assertEquals(3, argumentIterator.getIndex());

        } catch (ParameterParserException e) {
            out(e.getMessage());
            fail();
        }
    }

    @Test
    void parseLastFourOutOfBounds_neg() {

        ParameterParserMany argumentParserMany = new ParameterParserMany(4);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            argumentParserMany.parse(argumentIterator);
            fail();

        } catch (ParameterParserException e) {
            out("Expected Exception: " + e.getMessage());
        }
    }

    @Test
    void parseOutOfBounds_neg() {

        ParameterParserMany argumentParserMany = new ParameterParserMany(1);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 3);

        try {
            argumentParserMany.parse(argumentIterator);
            fail();

        } catch (ParameterParserException e) {
            out("Expected Exception: " + e.getMessage());
        }
    }

    private void out(String string) {
        if (OUT) System.out.println(string);
    }

}