package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.common.ArgumentIterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParameterParserVarTest {

    @Test
    void parseFirst() {

        ParameterParserVar argumentParserVar = new ParameterParserVar(0);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            argumentParserVar.parse(argumentIterator);

            assertEquals(4, argumentParserVar.getParameterList().size());
            assertEquals("A", argumentParserVar.getParameterList().get(0));
            assertEquals("B", argumentParserVar.getParameterList().get(1));
            assertEquals("C", argumentParserVar.getParameterList().get(2));
            assertEquals("D", argumentParserVar.getParameterList().get(3));

            assertEquals(3, argumentIterator.getIndex());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parseLast() {

        ParameterParserVar argumentParserVar = new ParameterParserVar(0);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 2);

        try {
            argumentParserVar.parse(argumentIterator);

            assertEquals(1, argumentParserVar.getParameterList().size());
            assertEquals("D", argumentParserVar.getParameterList().get(0));

            assertEquals(3, argumentIterator.getIndex());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parseMidMin2() {

        ParameterParserVar argumentParserVar = new ParameterParserVar(2);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 1);

        try {
            argumentParserVar.parse(argumentIterator);

            assertEquals(2, argumentParserVar.getParameterList().size());
            assertEquals("C", argumentParserVar.getParameterList().get(0));
            assertEquals("D", argumentParserVar.getParameterList().get(1));
            assertEquals(3, argumentIterator.getIndex());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parseMidMin3Fail() {

        ParameterParserVar argumentParserVar = new ParameterParserVar(3);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 1);

        try {
            argumentParserVar.parse(argumentIterator);
            fail();

        } catch (ParameterParserException e) {
            System.out.println("Expected Exception: " + e.getMessage());
        }
    }

    @Test
    void parseZero() {

        ParameterParserVar argumentParserVar = new ParameterParserVar(0);

        String[] args = {};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            argumentParserVar.parse(argumentIterator);

        } catch (ParameterParserException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

}