package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CliResultBuilder;
import de.arthurpicht.cli.PrintTestContext;
import de.arthurpicht.cli.TestOut;
import de.arthurpicht.cli.print.ParametersNMessage;
import de.arthurpicht.cli.print.ParametersOneMessage;
import de.arthurpicht.console.config.ConsoleConfiguration;
import de.arthurpicht.console.message.Message;
import de.arthurpicht.console.processor.StringComposer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParametersNTest {

    @Test
    public void getParser() {
        ParametersN parametersN = new ParametersN(2);
        assertNotNull(parametersN.getParameterParser(new CliResultBuilder(), "test"));
    }

    @Test
    public void initByNrOfParametersOnly() {
        ParametersN parametersN = new ParametersN(2);

        assertEquals(2, parametersN.getNrOfParameters());
        assertEquals("<parameter-1> <parameter-2>", parametersN.getHelpUsageSubString());

        String messageString = createMessageString(parametersN);
        assertEquals("  <parameter-1>                 \n  <parameter-2>                 ", messageString);
    }

    @Test
    public void initBySpecifiedGenericName() {
        ParametersN parametersN = new ParametersN(2, "test_name");

        assertEquals("<test_name-1> <test_name-2>", parametersN.getHelpUsageSubString());

        String messageString = createMessageString(parametersN);
        assertEquals("  <test_name-1>                 \n  <test_name-2>                 ", messageString);
    }

    @Test
    public void initByParameterList() {
        List<Parameter> parameterList = new ArrayList<>();
        parameterList.add(new Parameter("test-1", "first parameter"));
        parameterList.add(new Parameter("test-2", "second parameter"));

        ParametersN parametersN = new ParametersN(parameterList);

        assertEquals("<test-1> <test-2>", parametersN.getHelpUsageSubString());

        String messageStringExpected =
                "  <test-1>                      first parameter\n" +
                "  <test-2>                      second parameter";
        String messageString = createMessageString(parametersN);
        assertEquals(messageStringExpected, messageString);
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

        String messageString = createMessageString(parametersN);
        String messageStringExpected =
                "  <parameter-1>                 \n" +
                        "  <test-2>                      \n" +
                        "  <test-3>                      third parameter\n" +
                        "  <parameter-4>                 forth parameter";

        assertEquals(messageStringExpected, messageString);
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

        String messageString = createMessageString(parametersN);
        String messageStringExpected =
                "  <generic-1>                   \n" +
                "  <test-2>                      \n" +
                "  <test-3>                      third parameter\n" +
                "  <generic-4>                   forth parameter";
        assertEquals(messageStringExpected, messageString);
    }

    private String createMessageString(ParametersN parametersN) {
        Message message = ParametersNMessage.asMessage(parametersN);
        PrintTestContext printTestContext = new PrintTestContext();
        ConsoleConfiguration consoleConfiguration = printTestContext.getConsoleConfiguration();
        StringComposer stringComposer = new StringComposer(consoleConfiguration);
        return stringComposer.compose(message, StringComposer.Target.CONSOLE);
    }

}