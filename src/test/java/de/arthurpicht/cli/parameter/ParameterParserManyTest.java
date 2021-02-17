package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CommandLineInterfaceResultBuilder;
import de.arthurpicht.cli.common.ArgumentIterator;
import org.junit.jupiter.api.Test;

import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ParameterParserManyTest {

    @Test
    void parseFirst() {

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
        ParameterParserMany parameterParserMany = new ParameterParserMany(1, commandLineInterfaceResultBuilder);

        String[] args = {"A"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            parameterParserMany.parse(argumentIterator);

            assertEquals(1, parameterParserMany.getParserResult().getParameterList().size());
            assertEquals("A", parameterParserMany.getParserResult().getParameterList().get(0));
            assertEquals(0, argumentIterator.getIndex());

        } catch (ParameterParserException e) {
            println(e.getMessage());
            fail();
        }
    }

    @Test
    void parseFirstTwo() {

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
        ParameterParserMany parameterParserMany = new ParameterParserMany(2, commandLineInterfaceResultBuilder);

        String[] args = {"A", "B"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            parameterParserMany.parse(argumentIterator);
            assertEquals(2, parameterParserMany.getParserResult().getParameterList().size());
            assertEquals("A", parameterParserMany.getParserResult().getParameterList().get(0));
            assertEquals("B", parameterParserMany.getParserResult().getParameterList().get(1));
            assertEquals(1, argumentIterator.getIndex());

        } catch (ParameterParserException e) {
            println(e.getMessage());
            fail();
        }
    }

    @Test
    void parseLastThree() {

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
        ParameterParserMany parameterParserMany = new ParameterParserMany(3, commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            parameterParserMany.parse(argumentIterator);
            assertEquals(3, parameterParserMany.getParserResult().getParameterList().size());
            assertEquals("B", parameterParserMany.getParserResult().getParameterList().get(0));
            assertEquals("C", parameterParserMany.getParserResult().getParameterList().get(1));
            assertEquals("D", parameterParserMany.getParserResult().getParameterList().get(2));
            assertEquals(3, argumentIterator.getIndex());

        } catch (ParameterParserException e) {
            println(e.getMessage());
            fail();
        }
    }

    @Test
    void parseLastFourOutOfBounds_neg() {

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
        ParameterParserMany parameterParserMany = new ParameterParserMany(4, commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            parameterParserMany.parse(argumentIterator);
            fail(ParameterParserException.class.getSimpleName() + " expected.");

        } catch (ParameterParserException e) {
            println("Expected Exception: " + e.getMessage());
        }
    }

    @Test
    void parseOutOfBounds_neg() {

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
        ParameterParserMany parameterParserMany = new ParameterParserMany(1, commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 3);

        try {
            parameterParserMany.parse(argumentIterator);
            fail(ParameterParserException.class.getSimpleName() + " expected.");

        } catch (ParameterParserException e) {
            println("Expected Exception: " + e.getMessage());
        }
    }

}