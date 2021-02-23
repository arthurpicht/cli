package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CommandLineInterfaceResultBuilder;
import de.arthurpicht.cli.TestOut;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParametersOneTest {

    @Test
    public void getParser() {
        ParametersOne parametersOne = new ParametersOne();
        assertNotNull(parametersOne.getParameterParser(new CommandLineInterfaceResultBuilder()));
    }

    @Test
    public void initByDefaultConstructor() {
        ParametersOne parametersOne = new ParametersOne();

        TestOut.println(parametersOne.getHelpUsageSubString());
        TestOut.println(parametersOne.getHelpString());

        assertEquals("<parameter>", parametersOne.getHelpUsageSubString());
        assertEquals("<parameter>", parametersOne.getHelpString());
    }

    @Test
    public void initByParameter() {
        Parameter parameter = new Parameter("name", "description");
        ParametersOne parametersOne = new ParametersOne(parameter);

        TestOut.println(parametersOne.getHelpUsageSubString());
        TestOut.println(parametersOne.getHelpString());

        assertEquals("<name>", parametersOne.getHelpUsageSubString());
        assertEquals("<name>                        description", parametersOne.getHelpString());
    }

    @Test
    public void initByParameterizedConstructor() {
        ParametersOne parametersOne = new ParametersOne("name", "description");

        TestOut.println(parametersOne.getHelpUsageSubString());
        TestOut.println(parametersOne.getHelpString());

        assertEquals("<name>", parametersOne.getHelpUsageSubString());
        assertEquals("<name>                        description", parametersOne.getHelpString());
    }

}