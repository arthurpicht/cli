package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.common.ArgsHelper;
import de.arthurpicht.cli.common.ArgumentIterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParameterParserManyExceptionTest {

    @Test
    void parseInsufficientNumber() {

        ParameterParserMany parameterParserMany = new ParameterParserMany(3);

        String[] args = {"somethingElse", "A", "B"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            parameterParserMany.parse(argumentIterator);
            fail();

        } catch (IllegalNumberOfParametersException e) {

            System.out.println(e.getMessage());
            System.out.println(ArgsHelper.getArgsString(args));
            System.out.println(e.getArgumentPointerString());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void oneParameterSurplus() {

        ParameterParserMany parameterParserMany = new ParameterParserMany(3);

        String[] args = {"somethingElse", "A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            parameterParserMany.parse(argumentIterator);
            fail();

        } catch (IllegalNumberOfParametersException e) {

            assertEquals(3, Integer.parseInt(e.getNrParametersRequired()));
            assertEquals(4, e.getNrParametersFound());

            System.out.println(e.getMessage());
            System.out.println(ArgsHelper.getArgsString(args));
            System.out.println(e.getArgumentPointerString());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void twoParametersSurplus() {

        ParameterParserMany parameterParserMany = new ParameterParserMany(2);

        String[] args = {"somethingElse", "A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            parameterParserMany.parse(argumentIterator);
            fail();

        } catch (IllegalNumberOfParametersException e) {

            assertEquals(2, Integer.parseInt(e.getNrParametersRequired()));
            assertEquals(4, e.getNrParametersFound());

            System.out.println(e.getMessage());
            System.out.println(ArgsHelper.getArgsString(args));
            System.out.println(e.getArgumentPointerString());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail();
        }
    }



}
