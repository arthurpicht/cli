package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CommandLineInterfaceResultBuilder;
import de.arthurpicht.cli.TestOut;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParametersMinTest {

    @Test
    public void getParser() {
        ParametersMin parametersMin = new ParametersMin(2);
        assertNotNull(parametersMin.getParameterParser(new CommandLineInterfaceResultBuilder()));
    }

    @Test
    public void initByMin() {
        ParametersMin parametersMin = new ParametersMin(2);

        TestOut.println(parametersMin.getHelpUsageSubString());
        TestOut.println(parametersMin.getHelpString());

        assertEquals("2...n*<parameter>", parametersMin.getHelpUsageSubString());
        assertEquals("2...n*<parameter>", parametersMin.getHelpString());
    }

    @Test
    public void initByExtendedConstructor() {

        ParametersMin parametersMin = new ParametersMin(2, "test", "test description");

        TestOut.println(parametersMin.getHelpUsageSubString());
        TestOut.println(parametersMin.getHelpString());

        assertEquals("2...n*<test>", parametersMin.getHelpUsageSubString());
        assertEquals("2...n*<test>                  test description", parametersMin.getHelpString());
    }

}