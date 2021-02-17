package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CommandLineInterfaceResultBuilder;
import de.arthurpicht.cli.common.ArgumentIterator;
import org.junit.jupiter.api.Test;

import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParameterParserManyExceptionTest {

    @Test
    void parseInsufficientNumber_neg() {

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
        ParameterParserMany parameterParserMany = new ParameterParserMany(3, commandLineInterfaceResultBuilder);

        String[] args = {"somethingElse", "A", "B"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            parameterParserMany.parse(argumentIterator);
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

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
        ParameterParserMany parameterParserMany = new ParameterParserMany(3, commandLineInterfaceResultBuilder);

        String[] args = {"somethingElse", "A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            parameterParserMany.parse(argumentIterator);
            fail(IllegalNumberOfParametersException.class.getSimpleName() + " exptected.");

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

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
        ParameterParserMany parameterParserMany = new ParameterParserMany(2, commandLineInterfaceResultBuilder);

        String[] args = {"somethingElse", "A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            parameterParserMany.parse(argumentIterator);
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
