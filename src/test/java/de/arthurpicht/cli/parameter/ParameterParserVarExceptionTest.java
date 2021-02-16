package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.common.ArgumentIterator;
import org.junit.jupiter.api.Test;

import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParameterParserVarExceptionTest {

    @Test
    void emptyArgs() {

        ParameterParserVar argumentParserVar = new ParameterParserVar(1);

        String[] args = {};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            argumentParserVar.parse(argumentIterator);
            fail();

        } catch (ParameterParserException e) {
            println(e.getMessage());
            println(e.getArgsAsString());
            println(e.getArgumentPointerString());

            assertEquals("Wrong number of parameters. Minimal number expected: 1, found: 0.", e.getMessage());
            assertEquals("^", e.getArgumentPointerString());
        }
    }

    @Test
    void parseFirst() {

        ParameterParserVar argumentParserVar = new ParameterParserVar(5);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            argumentParserVar.parse(argumentIterator);
            fail();

        } catch (ParameterParserException e) {
            println(e.getMessage());
            println(e.getArgsAsString());
            println(e.getArgumentPointerString());

            assertEquals("Wrong number of parameters. Minimal number expected: 5, found: 4.", e.getMessage());
            assertEquals("        ^", e.getArgumentPointerString());
        }
    }

}
