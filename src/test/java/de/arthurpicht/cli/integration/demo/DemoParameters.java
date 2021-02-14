package de.arthurpicht.cli.integration.demo;

import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.CommandLineInterfaceBuilder;
import de.arthurpicht.cli.CommandLineInterfaceCall;
import de.arthurpicht.cli.CommandLineInterfaceResult;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
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
                .add(new CommandSequenceBuilder().addCommand("add").withSpecificOptions(specificOptionsAddUser).build())
                .add(new CommandSequenceBuilder().addCommand("delete").withSpecificOptions(specificOptionsDeleteUser).build())
                .add(new CommandSequenceBuilder().addCommand("show").withSpecificOptions(specificOptionsShow).withParameters(new ParametersOne()).build());

        return new CommandLineInterfaceBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .build("test");
    }

    @Test
    void mostSimpleCommandWithNoParameter() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"add"};

        CommandLineInterfaceCall commandLineInterfaceCall = commandLineInterface.parse(args);
        CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

        assertTrue(commandLineInterfaceResult.getOptionParserResultGlobal().isEmpty());

        List<String> commandList = commandLineInterfaceCall.getCommandList();
        assertEquals(1, commandList.size());
        assertEquals("add", commandList.get(0));

        assertTrue(commandLineInterfaceResult.getOptionParserResultSpecific().isEmpty());
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

        CommandLineInterfaceCall commandLineInterfaceCall = commandLineInterface.parse(args);
        CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

        assertTrue(commandLineInterfaceResult.getOptionParserResultGlobal().isEmpty());

        List<String> commandList = commandLineInterfaceCall.getCommandList();
        assertEquals(1, commandList.size());
        assertEquals("show", commandList.get(0));

        assertTrue(commandLineInterfaceResult.getOptionParserResultSpecific().isEmpty());

        assertEquals(1, commandLineInterfaceResult.getParameterParserResult().getNrOfParameters());
        assertEquals("123", commandLineInterfaceResult.getParameterParserResult().getParameterList().get(0));
    }

    @Test
    void globalOptionAndParameter() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"-d", "show", "123"};

        CommandLineInterfaceCall commandLineInterfaceCall = commandLineInterface.parse(args);
        CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

        assertFalse(commandLineInterfaceResult.getOptionParserResultGlobal().isEmpty());
        assertTrue(commandLineInterfaceResult.getOptionParserResultGlobal().hasOption("DEBUG"));
        assertEquals(1, commandLineInterfaceResult.getOptionParserResultGlobal().getSize());

        List<String> commandList = commandLineInterfaceCall.getCommandList();
        assertEquals(1, commandList.size());
        assertEquals("show", commandList.get(0));

        assertTrue(commandLineInterfaceResult.getOptionParserResultSpecific().isEmpty());

        assertEquals(1, commandLineInterfaceResult.getParameterParserResult().getNrOfParameters());
        assertEquals("123", commandLineInterfaceResult.getParameterParserResult().getParameterList().get(0));
    }

    @Test
    void globalOptionAndSpecificOptionAndParameter() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"-d", "show", "-v", "123"};

        CommandLineInterfaceCall commandLineInterfaceCall = commandLineInterface.parse(args);
        CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

        assertFalse(commandLineInterfaceResult.getOptionParserResultGlobal().isEmpty());
        assertTrue(commandLineInterfaceResult.getOptionParserResultGlobal().hasOption("DEBUG"));
        assertEquals(1, commandLineInterfaceResult.getOptionParserResultGlobal().getSize());

        List<String> commandList = commandLineInterfaceCall.getCommandList();
        assertEquals(1, commandList.size());
        assertEquals("show", commandList.get(0));

        assertFalse(commandLineInterfaceResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(commandLineInterfaceResult.getOptionParserResultSpecific().hasOption("VERBOSE"));
        assertEquals(1, commandLineInterfaceResult.getOptionParserResultSpecific().getSize());

        assertEquals(1, commandLineInterfaceResult.getParameterParserResult().getNrOfParameters());
        assertEquals("123", commandLineInterfaceResult.getParameterParserResult().getParameterList().get(0));
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
