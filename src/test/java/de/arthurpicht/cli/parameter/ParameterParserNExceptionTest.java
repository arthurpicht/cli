package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CliResultBuilder;
import de.arthurpicht.cli.common.ArgumentIterator;
import org.junit.jupiter.api.Test;

import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParameterParserNExceptionTest {

    @Test
    void parseInsufficientNumber_neg() {

        CliResultBuilder cliResultBuilder = new CliResultBuilder();
        ParameterParserN parameterParserN = new ParameterParserN(3, cliResultBuilder, "test");

        String[] args = {"somethingElse", "A", "B"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            parameterParserN.parse(argumentIterator);
            fail(IllegalNumberOfParametersException.class.getSimpleName() + " expected.");

        } catch (IllegalNumberOfParametersException e) {

            println(e.getMessage());
            println(e.getArgsAsString());
            println(e.getArgumentPointerString());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void oneParameterSurplus_neg() {

        CliResultBuilder cliResultBuilder = new CliResultBuilder();
        ParameterParserN parameterParserN = new ParameterParserN(3, cliResultBuilder, "test");

        String[] args = {"somethingElse", "A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            parameterParserN.parse(argumentIterator);
            fail(IllegalNumberOfParametersException.class.getSimpleName() + " expected.");

        } catch (IllegalNumberOfParametersException e) {

            assertEquals(3, Integer.parseInt(e.getNrParametersRequired()));
            assertEquals(4, e.getNrParametersFound());

            println(e.getMessage());
            println(e.getArgsAsString());
            println(e.getArgumentPointerString());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void twoParametersSurplus() {

        CliResultBuilder cliResultBuilder = new CliResultBuilder();
        ParameterParserN parameterParserN = new ParameterParserN(2, cliResultBuilder, "test");

        String[] args = {"somethingElse", "A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            parameterParserN.parse(argumentIterator);
            fail(IllegalNumberOfParametersException.class.getSimpleName() + " expected.");

        } catch (IllegalNumberOfParametersException e) {

            assertEquals(2, Integer.parseInt(e.getNrParametersRequired()));
            assertEquals(4, e.getNrParametersFound());

            println(e.getMessage());
            println(e.getArgsAsString());
            println(e.getArgumentPointerString());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail();
        }
    }

}
