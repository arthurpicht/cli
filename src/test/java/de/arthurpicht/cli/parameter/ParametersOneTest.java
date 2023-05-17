package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CliResultBuilder;
import de.arthurpicht.cli.PrintTestContext;
import de.arthurpicht.cli.TestOut;
import de.arthurpicht.cli.print.ParametersOneMessage;
import de.arthurpicht.console.config.ConsoleConfiguration;
import de.arthurpicht.console.message.Message;
import de.arthurpicht.console.processor.StringComposer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParametersOneTest {

    @Test
    public void getParser() {
        ParametersOne parametersOne = new ParametersOne();
        assertNotNull(parametersOne.getParameterParser(new CliResultBuilder(), "test"));
    }

    @Test
    public void initByDefaultConstructor() {
        ParametersOne parametersOne = new ParametersOne();

        TestOut.println(parametersOne.getHelpUsageSubString());
        TestOut.println(parametersOne.getHelpString());

        assertEquals("<parameter>", parametersOne.getHelpUsageSubString());
        assertEquals("<parameter>", parametersOne.getHelpString());

        String string = createMessageString(parametersOne);
        assertEquals("<parameter>                   ", string);
    }

    @Test
    public void initByParameter() {
        Parameter parameter = new Parameter("name", "description");
        ParametersOne parametersOne = new ParametersOne(parameter);

        TestOut.println(parametersOne.getHelpUsageSubString());
        TestOut.println(parametersOne.getHelpString());

        assertEquals("<name>", parametersOne.getHelpUsageSubString());
        assertEquals("<name>                        description", parametersOne.getHelpString());

        String string = createMessageString(parametersOne);
        assertEquals("<name>                        description", string);
    }

    @Test
    public void initByParameterizedConstructor() {
        ParametersOne parametersOne = new ParametersOne("name", "description");

        TestOut.println(parametersOne.getHelpUsageSubString());
        TestOut.println(parametersOne.getHelpString());

        assertEquals("<name>", parametersOne.getHelpUsageSubString());
        assertEquals("<name>                        description", parametersOne.getHelpString());

        String string = createMessageString(parametersOne);
        assertEquals("<name>                        description", string);
    }

    private String createMessageString(ParametersOne parametersOne) {
        Message message = ParametersOneMessage.asMessage(parametersOne);
        PrintTestContext printTestContext = new PrintTestContext();
        ConsoleConfiguration consoleConfiguration = printTestContext.getConsoleConfiguration();
        StringComposer stringComposer = new StringComposer(consoleConfiguration);
        return stringComposer.compose(message, StringComposer.Target.CONSOLE);
    }

}