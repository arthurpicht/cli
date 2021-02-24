package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CliResultBuilder;
import de.arthurpicht.cli.TestOut;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParametersNTest {

    @Test
    public void getParser() {
        ParametersN parametersN = new ParametersN(2);
        assertNotNull(parametersN.getParameterParser(new CliResultBuilder()));
    }

    @Test
    public void initByNrOfParametersOnly() {

        ParametersN parametersN = new ParametersN(2);

        assertEquals(2, parametersN.getNrOfParameters());
        assertEquals("<parameter-1> <parameter-2>", parametersN.getHelpUsageSubString());
        assertEquals("<parameter-1>\n<parameter-2>", parametersN.getHelpString());
    }

    @Test
    public void initBySpecifiedGenericName() {

        ParametersN parametersN = new ParametersN(2, "test_name");

        assertEquals("<test_name-1> <test_name-2>", parametersN.getHelpUsageSubString());
        assertEquals("<test_name-1>\n<test_name-2>", parametersN.getHelpString());
    }

    @Test
    public void initByParameterList() {

        List<Parameter> parameterList = new ArrayList<>();
        parameterList.add(new Parameter("test-1", "first parameter"));
        parameterList.add(new Parameter("test-2", "second parameter"));

        ParametersN parametersN = new ParametersN(parameterList);

        assertEquals("<test-1> <test-2>", parametersN.getHelpUsageSubString());

        String helpString =
                "<test-1>                      first parameter\n" +
                "<test-2>                      second parameter";
        assertEquals(helpString, parametersN.getHelpString());
    }

    @Test
    public void initByBuilder() {

        ParametersN parametersN = new ParametersNBuilder()
                .addParameter()
                .addParameter("test-2")
                .addParameter("test-3", "third parameter")
                .addParameterByDescriptionOnly("forth parameter")
                .build();

        assertEquals("<parameter-1> <test-2> <test-3> <parameter-4>", parametersN.getHelpUsageSubString());

        String helpString =
                "<parameter-1>\n" +
                "<test-2>\n" +
                "<test-3>                      third parameter\n" +
                "<parameter-4>                 forth parameter";
        assertEquals(helpString, parametersN.getHelpString());
    }

    @Test
    public void initByBuilderWithGenericNameSpecified() {

        ParametersN parametersN = new ParametersNBuilder()
                .addParameter()
                .addParameter("test-2")
                .addParameter("test-3", "third parameter")
                .addParameterByDescriptionOnly("forth parameter")
                .withGenericName("generic")
                .build();

        assertEquals("<generic-1> <test-2> <test-3> <generic-4>", parametersN.getHelpUsageSubString());

        String helpString =
                "<generic-1>\n" +
                        "<test-2>\n" +
                        "<test-3>                      third parameter\n" +
                        "<generic-4>                   forth parameter";

        TestOut.println(parametersN.getHelpString());

        assertEquals(helpString, parametersN.getHelpString());
    }

}