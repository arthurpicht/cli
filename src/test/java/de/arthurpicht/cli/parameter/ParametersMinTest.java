package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CliResultBuilder;
import de.arthurpicht.cli.PrintTestContext;
import de.arthurpicht.cli.TestOut;
import de.arthurpicht.cli.print.ParametersMinMessage;
import de.arthurpicht.console.config.ConsoleConfiguration;
import de.arthurpicht.console.message.Message;
import de.arthurpicht.console.processor.StringComposer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParametersMinTest {

    @Test
    public void getParser() {
        ParametersMin parametersMin = new ParametersMin(2);
        assertNotNull(parametersMin.getParameterParser(new CliResultBuilder(), "test"));
    }

    @Test
    public void initByMin() {
        ParametersMin parametersMin = new ParametersMin(2);

        TestOut.println(parametersMin.getHelpUsageSubString());
        TestOut.println(parametersMin.getHelpString());
        assertEquals("2..n*<parameter>", parametersMin.getHelpUsageSubString());

        String string = createMessageString(parametersMin);
        assertEquals("2..n*<parameter>              ", string);

        assertEquals("2..n*<parameter>", parametersMin.getHelpString());
    }

    @Test
    public void initByExtendedConstructor() {

        ParametersMin parametersMin = new ParametersMin(2, "test", "test description");

        TestOut.println(parametersMin.getHelpUsageSubString());
        TestOut.println(parametersMin.getHelpString());
        assertEquals("2..n*<test>", parametersMin.getHelpUsageSubString());

        String string = createMessageString(parametersMin);
        assertEquals("2..n*<test>                   test description", string);

        assertEquals("2..n*<test>                   test description", parametersMin.getHelpString());
    }

    private String createMessageString(ParametersMin parametersMin) {
        Message message = ParametersMinMessage.asMessage(parametersMin);
        PrintTestContext printTestContext = new PrintTestContext();
        ConsoleConfiguration consoleConfiguration = printTestContext.getConsoleConfiguration();
        StringComposer stringComposer = new StringComposer(consoleConfiguration);
        return stringComposer.compose(message, StringComposer.Target.CONSOLE);
    }

}