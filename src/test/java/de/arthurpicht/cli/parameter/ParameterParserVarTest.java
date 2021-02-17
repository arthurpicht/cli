package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CommandLineInterfaceResultBuilder;
import de.arthurpicht.cli.common.ArgumentIterator;
import org.junit.jupiter.api.Test;

import static de.arthurpicht.cli.TestOut.printStacktrace;
import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.*;

class ParameterParserVarTest {

    @Test
    void parseFirst() {

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
        ParameterParserVar parameterParserVar = new ParameterParserVar(0, commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            parameterParserVar.parse(argumentIterator);

            assertEquals(4, parameterParserVar.getParserResult().getParameterList().size());
            assertEquals("A", parameterParserVar.getParserResult().getParameterList().get(0));
            assertEquals("B", parameterParserVar.getParserResult().getParameterList().get(1));
            assertEquals("C", parameterParserVar.getParserResult().getParameterList().get(2));
            assertEquals("D", parameterParserVar.getParserResult().getParameterList().get(3));

            assertEquals(3, argumentIterator.getIndex());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @Test
    void parseLast() {

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
        ParameterParserVar parameterParserVar = new ParameterParserVar(0, commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 2);

        try {
            parameterParserVar.parse(argumentIterator);

            assertEquals(1, parameterParserVar.getParserResult().getParameterList().size());
            assertEquals("D", parameterParserVar.getParserResult().getParameterList().get(0));

            assertEquals(3, argumentIterator.getIndex());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @Test
    void parseMidMin2() {

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
        ParameterParserVar parameterParserVar = new ParameterParserVar(2, commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 1);

        try {
            parameterParserVar.parse(argumentIterator);

            assertEquals(2, parameterParserVar.getParserResult().getParameterList().size());
            assertEquals("C", parameterParserVar.getParserResult().getParameterList().get(0));
            assertEquals("D", parameterParserVar.getParserResult().getParameterList().get(1));
            assertEquals(3, argumentIterator.getIndex());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @Test
    void parseMidMin3Fail() {

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
        ParameterParserVar parameterParserVar = new ParameterParserVar(3, commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 1);

        try {
            parameterParserVar.parse(argumentIterator);
            fail();

        } catch (ParameterParserException e) {
            println("Expected Exception: " + e.getMessage());
        }
    }

    @Test
    void parseZero() {

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
        ParameterParserVar argumentParserVar = new ParameterParserVar(0, commandLineInterfaceResultBuilder);

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