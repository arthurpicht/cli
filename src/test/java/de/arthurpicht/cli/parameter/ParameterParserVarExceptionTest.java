package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.common.ArgsHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParameterParserVarExceptionTest {

    @Test
    void emptyArgs() {

        ParameterParserVar argumentParserVar = new ParameterParserVar(1);

        String[] args = {};

        try {
            argumentParserVar.parse(args, 0);
            fail();

        } catch (ParameterParserException e) {
            System.out.println(e.getMessage());
            System.out.println(ArgsHelper.getArgsString(args));
            System.out.println(e.getArgumentPointerString());

            assertEquals("Illegal number of arguments. Minimal number expected: 1, found: 0.", e.getMessage());
            assertEquals("^", e.getArgumentPointerString());


        }
    }

    @Test
    void parseFirst() {

        ParameterParserVar argumentParserVar = new ParameterParserVar(5);

        String[] args = {"A", "B", "C", "D"};

        try {
            argumentParserVar.parse(args, 0);
            fail();

        } catch (ParameterParserException e) {
            System.out.println(e.getMessage());
            System.out.println(ArgsHelper.getArgsString(args));
            System.out.println(e.getArgumentPointerString());

            assertEquals("Illegal number of arguments. Minimal number expected: 5, found: 4.", e.getMessage());
            assertEquals("        ^", e.getArgumentPointerString());
        }
    }


}
