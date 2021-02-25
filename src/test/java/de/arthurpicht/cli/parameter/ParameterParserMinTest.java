package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CliResultBuilder;
import de.arthurpicht.cli.common.ArgumentIterator;
import org.junit.jupiter.api.Test;

import static de.arthurpicht.cli.TestOut.printStacktrace;
import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.*;

class ParameterParserMinTest {

    @Test
    void parseFirst() {

        CliResultBuilder cliResultBuilder = new CliResultBuilder();
        ParameterParserMin parameterParserMin = new ParameterParserMin(0, cliResultBuilder, "test");

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            parameterParserMin.parse(argumentIterator);

            assertEquals(4, parameterParserMin.getParserResult().getParameterList().size());
            assertEquals("A", parameterParserMin.getParserResult().getParameterList().get(0));
            assertEquals("B", parameterParserMin.getParserResult().getParameterList().get(1));
            assertEquals("C", parameterParserMin.getParserResult().getParameterList().get(2));
            assertEquals("D", parameterParserMin.getParserResult().getParameterList().get(3));

            assertEquals(3, argumentIterator.getIndex());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @Test
    void parseLast() {

        CliResultBuilder cliResultBuilder = new CliResultBuilder();
        ParameterParserMin parameterParserMin = new ParameterParserMin(0, cliResultBuilder, "test");

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 2);

        try {
            parameterParserMin.parse(argumentIterator);

            assertEquals(1, parameterParserMin.getParserResult().getParameterList().size());
            assertEquals("D", parameterParserMin.getParserResult().getParameterList().get(0));

            assertEquals(3, argumentIterator.getIndex());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @Test
    void parseMidMin2() {

        CliResultBuilder cliResultBuilder = new CliResultBuilder();
        ParameterParserMin parameterParserMin = new ParameterParserMin(2, cliResultBuilder, "test");

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 1);

        try {
            parameterParserMin.parse(argumentIterator);

            assertEquals(2, parameterParserMin.getParserResult().getParameterList().size());
            assertEquals("C", parameterParserMin.getParserResult().getParameterList().get(0));
            assertEquals("D", parameterParserMin.getParserResult().getParameterList().get(1));
            assertEquals(3, argumentIterator.getIndex());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @Test
    void parseMidMin3Fail() {

        CliResultBuilder cliResultBuilder = new CliResultBuilder();
        ParameterParserMin parameterParserMin = new ParameterParserMin(3, cliResultBuilder, "test");

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 1);

        try {
            parameterParserMin.parse(argumentIterator);
            fail();

        } catch (ParameterParserException e) {
            println("Expected Exception: " + e.getMessage());
        }
    }

    @Test
    void parseZero() {

        CliResultBuilder cliResultBuilder = new CliResultBuilder();
        ParameterParserMin argumentParserVar = new ParameterParserMin(0, cliResultBuilder, "test");

        String[] args = {};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            argumentParserVar.parse(argumentIterator);

        } catch (ParameterParserException e) {
            println(e.getMessage());
            printStacktrace(e);
            fail();
        }
    }

}