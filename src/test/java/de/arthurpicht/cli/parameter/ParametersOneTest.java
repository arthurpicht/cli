package de.arthurpicht.cli.parameter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParametersOneTest {

    @Test
    public void initByDefaultConstructor() {
        ParametersOne parametersOne = new ParametersOne();

        assertTrue(parametersOne.getParameterParser() instanceof ParameterParserOne);
        assertEquals("<parameter>", parametersOne.getHelpUsageSubString());
        assertEquals("<parameter>", parametersOne.getHelpString());
    }

    @Test
    public void initByParameter() {
        Parameter parameter = new Parameter("name", "description");
        ParametersOne parametersOne = new ParametersOne(parameter);

        assertEquals("<name>", parametersOne.getHelpUsageSubString());
        assertEquals("<name>                   description", parametersOne.getHelpString());
    }

    @Test
    public void initByParameterizedConstructor() {
        ParametersOne parametersOne = new ParametersOne("name", "description");

        assertEquals("<name>", parametersOne.getHelpUsageSubString());
        assertEquals("<name>                   description", parametersOne.getHelpString());
    }

}