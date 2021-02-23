package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CommandLineInterfaceResultBuilder;
import de.arthurpicht.cli.common.ArgumentIterator;
import org.junit.jupiter.api.Test;

import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParameterParserMinExceptionTest {

    @Test
    void emptyArgs_neg() {

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
        ParameterParserMin parameterParserMin = new ParameterParserMin(1, commandLineInterfaceResultBuilder);

        String[] args = {};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            parameterParserMin.parse(argumentIterator);
            fail(ParameterParserException.class.getSimpleName() + " expected.");

        } catch (ParameterParserException e) {
            println(e.getMessage());
            println(e.getArgsAsString());
            println(e.getArgumentPointerString());

            assertEquals("Wrong number of parameters. Minimal number expected: 1, found: 0.", e.getMessage());
            assertEquals("^", e.getArgumentPointerString());
        }
    }

    @Test
    void parseFirst_neg() {

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
        ParameterParserMin argumentParserVar = new ParameterParserMin(5, commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            argumentParserVar.parse(argumentIterator);
            fail(ParameterParserException.class.getSimpleName() + " expected.");

        } catch (ParameterParserException e) {
            println(e.getMessage());
            println(e.getArgsAsString());
            println(e.getArgumentPointerString());

            assertEquals("Wrong number of parameters. Minimal number expected: 5, found: 4.", e.getMessage());
            assertEquals("        ^", e.getArgumentPointerString());
        }
    }

}
