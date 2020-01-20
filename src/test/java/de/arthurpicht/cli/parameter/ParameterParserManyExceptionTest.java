package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.common.ArgsHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParameterParserManyExceptionTest {

    @Test
    void parseInsufficientNumber() {

        ParameterParserMany argumentParserMany = new ParameterParserMany(3);

        String[] args = {"somethingElse", "A", "B"};

        try {
            argumentParserMany.parse(args, 1);
            fail();

//            assertEquals(1, argumentParserMany.getParameterList().size());
//            assertEquals("A", argumentParserMany.getParameterList().get(0));
//            assertEquals(0, argumentParserMany.getLastProcessedIndex());

        } catch (IllegalNumberOfParametersException e) {

            System.out.println(e.getMessage());
            System.out.println(ArgsHelper.getArgsString(args));
            System.out.println(e.getArgumentPointerString());

        } catch (ParameterParserException e) {
            e.printStackTrace();
            fail();
        }
    }


}
