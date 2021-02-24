package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CliResultBuilder;
import de.arthurpicht.cli.common.ArgumentIterator;
import org.junit.jupiter.api.Test;

import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ParameterParserNTest {

    @Test
    void parseFirst() {

        CliResultBuilder cliResultBuilder = new CliResultBuilder();
        ParameterParserN parameterParserN = new ParameterParserN(1, cliResultBuilder);

        String[] args = {"A"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            parameterParserN.parse(argumentIterator);

            assertEquals(1, parameterParserN.getParserResult().getParameterList().size());
            assertEquals("A", parameterParserN.getParserResult().getParameterList().get(0));
            assertEquals(0, argumentIterator.getIndex());

        } catch (ParameterParserException e) {
            println(e.getMessage());
            fail();
        }
    }

    @Test
    void parseFirstTwo() {

        CliResultBuilder cliResultBuilder = new CliResultBuilder();
        ParameterParserN parameterParserN = new ParameterParserN(2, cliResultBuilder);

        String[] args = {"A", "B"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            parameterParserN.parse(argumentIterator);
            assertEquals(2, parameterParserN.getParserResult().getParameterList().size());
            assertEquals("A", parameterParserN.getParserResult().getParameterList().get(0));
            assertEquals("B", parameterParserN.getParserResult().getParameterList().get(1));
            assertEquals(1, argumentIterator.getIndex());

        } catch (ParameterParserException e) {
            println(e.getMessage());
            fail();
        }
    }

    @Test
    void parseLastThree() {

        CliResultBuilder cliResultBuilder = new CliResultBuilder();
        ParameterParserN parameterParserN = new ParameterParserN(3, cliResultBuilder);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            parameterParserN.parse(argumentIterator);
            assertEquals(3, parameterParserN.getParserResult().getParameterList().size());
            assertEquals("B", parameterParserN.getParserResult().getParameterList().get(0));
            assertEquals("C", parameterParserN.getParserResult().getParameterList().get(1));
            assertEquals("D", parameterParserN.getParserResult().getParameterList().get(2));
            assertEquals(3, argumentIterator.getIndex());

        } catch (ParameterParserException e) {
            println(e.getMessage());
            fail();
        }
    }

    @Test
    void parseLastFourOutOfBounds_neg() {

        CliResultBuilder cliResultBuilder = new CliResultBuilder();
        ParameterParserN parameterParserN = new ParameterParserN(4, cliResultBuilder);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            parameterParserN.parse(argumentIterator);
            fail(ParameterParserException.class.getSimpleName() + " expected.");

        } catch (ParameterParserException e) {
            println("Expected Exception: " + e.getMessage());
        }
    }

    @Test
    void parseOutOfBounds_neg() {

        CliResultBuilder cliResultBuilder = new CliResultBuilder();
        ParameterParserN parameterParserN = new ParameterParserN(1, cliResultBuilder);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 3);

        try {
            parameterParserN.parse(argumentIterator);
            fail(ParameterParserException.class.getSimpleName() + " expected.");

        } catch (ParameterParserException e) {
            println("Expected Exception: " + e.getMessage());
        }
    }

}