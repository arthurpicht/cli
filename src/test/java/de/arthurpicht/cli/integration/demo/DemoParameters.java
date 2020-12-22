package de.arthurpicht.cli.integration.demo;

import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.CommandLineInterfaceBuilder;
import de.arthurpicht.cli.ParserResult;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.ParametersOne;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.*;

public class DemoParameters {

    private static final boolean OUT = false;

    @SuppressWarnings("SpellCheckingInspection")
    private CommandLineInterface getCommandLineInterface() {

        Options globalOptions = new Options()
                .add(new OptionBuilder().withShortName('d').withLongName("debug").withDescription("debug").build("DEBUG"))
                .add(new OptionBuilder().withLongName("loglevel").hasArgument().build("LOG_LEVEL"));

        Options specificOptionsAddUser = new Options()
                .add(new OptionBuilder().withShortName('u').withLongName("username").hasArgument().build("USERNAME"))
                .add(new OptionBuilder().withShortName('p').withLongName("password").hasArgument().build("PASSWORD"));

        Options specificOptionsDeleteUser = new Options()
                .add(new OptionBuilder().withShortName('u').withLongName("username").hasArgument().build("USERNAME"))
                .add(new OptionBuilder().withLongName("doit").build("DOIT"));

        Options specificOptionsShow = new Options()
                .add(new OptionBuilder().withShortName('v').withLongName("verbose").build("VERBOSE"));

        Commands commands = new Commands()
                .add("add")
                .withSpecificOptions(specificOptionsAddUser)
                .reset().add("delete")
                .withSpecificOptions(specificOptionsDeleteUser)
                .reset().add("show")
                .withSpecificOptions(specificOptionsShow)
                .withParameters(new ParametersOne());

        return new CommandLineInterfaceBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .build();
    }

    @Test
    void mostSimpleCommandWithNoParameter() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"add"};

        ParserResult parserResult = commandLineInterface.parse(args);

        assertTrue(parserResult.getOptionParserResultGlobal().isEmpty());

        List<String> commandList = parserResult.getCommandList();
        assertEquals(1, commandList.size());
        assertEquals("add", commandList.get(0));

        assertTrue(parserResult.getOptionParserResultSpecific().isEmpty());
    }

    @Test
    void mostSimpleCommandWithParameter_neg() {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"show"};

        try {
            commandLineInterface.parse(args);
            fail("Parameter is missing. Exception expected.");
        } catch (UnrecognizedArgumentException e) {
            // din
            println(e.getMessage());
        }
    }

    @Test
    void mostSimpleCommandWithParameter() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"show", "123"};

        ParserResult parserResult = commandLineInterface.parse(args);

        assertTrue(parserResult.getOptionParserResultGlobal().isEmpty());

        List<String> commandList = parserResult.getCommandList();
        assertEquals(1, commandList.size());
        assertEquals("show", commandList.get(0));

        assertTrue(parserResult.getOptionParserResultSpecific().isEmpty());

        assertEquals(1, parserResult.getParameterList().size());
        assertEquals("123", parserResult.getParameterList().get(0));
    }

    @Test
    void globalOptionAndParameter() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"-d", "show", "123"};

        ParserResult parserResult = commandLineInterface.parse(args);

        assertFalse(parserResult.getOptionParserResultGlobal().isEmpty());
        assertTrue(parserResult.getOptionParserResultGlobal().hasOption("DEBUG"));
        assertEquals(1, parserResult.getOptionParserResultGlobal().getSize());

        List<String> commandList = parserResult.getCommandList();
        assertEquals(1, commandList.size());
        assertEquals("show", commandList.get(0));

        assertTrue(parserResult.getOptionParserResultSpecific().isEmpty());

        assertEquals(1, parserResult.getParameterList().size());
        assertEquals("123", parserResult.getParameterList().get(0));
    }

    @Test
    void globalOptionAndSpecificOptionAndParameter() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"-d", "show", "-v", "123"};

        ParserResult parserResult = commandLineInterface.parse(args);

        assertFalse(parserResult.getOptionParserResultGlobal().isEmpty());
        assertTrue(parserResult.getOptionParserResultGlobal().hasOption("DEBUG"));
        assertEquals(1, parserResult.getOptionParserResultGlobal().getSize());

        List<String> commandList = parserResult.getCommandList();
        assertEquals(1, commandList.size());
        assertEquals("show", commandList.get(0));

        assertFalse(parserResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(parserResult.getOptionParserResultSpecific().hasOption("VERBOSE"));
        assertEquals(1, parserResult.getOptionParserResultSpecific().getSize());

        assertEquals(1, parserResult.getParameterList().size());
        assertEquals("123", parserResult.getParameterList().get(0));
    }

    @Test
    void globalOptionAndSpecificOptionAndParameter_neg() {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"-d", "show", "-v"};

        try {
            commandLineInterface.parse(args);
            fail("Parameter is missing. Exception expected.");
        } catch (UnrecognizedArgumentException e) {
            // din
            println(e.getMessage());
        }
    }

}
