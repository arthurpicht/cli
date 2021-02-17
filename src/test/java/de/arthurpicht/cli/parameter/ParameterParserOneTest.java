package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CommandLineInterfaceResultBuilder;
import de.arthurpicht.cli.common.ArgumentIterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ParameterParserOneTest {

    @Test
    void parseFirst() throws ParameterParserException {

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
        ParameterParserOne parameterParserOne = new ParameterParserOne(commandLineInterfaceResultBuilder);
        parameterParserOne.parse(argumentIterator);

        assertEquals("A", parameterParserOne.getParserResult().getParameterList().get(0));
        assertEquals(0, argumentIterator.getIndex());
    }

    @Test
    void parseLast() throws ParameterParserException {

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 2);

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
        ParameterParserOne parameterParserOne = new ParameterParserOne(commandLineInterfaceResultBuilder);
        parameterParserOne.parse(argumentIterator);

        assertEquals("D", parameterParserOne.getParameterList().get(0));
        assertEquals(3, argumentIterator.getIndex());
    }

    @Test
    void parseOutOfBounds_neg() {

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 3);

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
        ParameterParserOne argumentParserOne = new ParameterParserOne(commandLineInterfaceResultBuilder);
        try {
            argumentParserOne.parse(argumentIterator);
            fail(ParameterParserException.class.getSimpleName() + " expected.");
        } catch (ParameterParserException e) {
            // din
        }
    }

}