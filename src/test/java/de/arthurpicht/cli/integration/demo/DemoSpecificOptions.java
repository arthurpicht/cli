package de.arthurpicht.cli.integration.demo;

import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.CommandLineInterfaceBuilder;
import de.arthurpicht.cli.CommandLineInterfaceCall;
import de.arthurpicht.cli.CommandLineInterfaceResult;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.option.Options;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CLI with global options and two commands, each with different specific commands.
 */
@SuppressWarnings("SpellCheckingInspection")
public class DemoSpecificOptions {

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

        Commands commands = new Commands()
                .add(new CommandSequenceBuilder().addCommand("addUser").withSpecificOptions(specificOptionsAddUser).build())
                .add(new CommandSequenceBuilder().addCommand("deleteUser").withSpecificOptions(specificOptionsDeleteUser).build());

        return new CommandLineInterfaceBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .build("test");
    }

    @Test
    void mostSimple() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"addUser"};

        CommandLineInterfaceCall commandLineInterfaceCall = commandLineInterface.parse(args);
        CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

        assertTrue(commandLineInterfaceResult.getOptionParserResultGlobal().isEmpty());

        List<String> commandList = commandLineInterfaceResult.getCommandParserResult().getCommandStringList();
        assertEquals(1, commandList.size());
        assertEquals("addUser", commandList.get(0));

        assertTrue(commandLineInterfaceResult.getOptionParserResultSpecific().isEmpty());
    }

    @Test
    void globalOptions() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"-d", "addUser"};

        CommandLineInterfaceCall commandLineInterfaceCall = commandLineInterface.parse(args);
        CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

        OptionParserResult optionParserResult = commandLineInterfaceResult.getOptionParserResultGlobal();
        assertFalse(optionParserResult.isEmpty());
        assertEquals(optionParserResult.getSize(), 1);
        assertTrue(optionParserResult.hasOption("DEBUG"));

        List<String> commandList = commandLineInterfaceResult.getCommandParserResult().getCommandStringList();
        assertEquals(1, commandList.size());
        assertEquals("addUser", commandList.get(0));

        assertTrue(commandLineInterfaceResult.getOptionParserResultSpecific().isEmpty());
    }

    @Test
    void specificOptionForFirstCommand() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"-d", "addUser", "--username", "dummy", "--password", "topsecret"};

        CommandLineInterfaceCall commandLineInterfaceCall = commandLineInterface.parse(args);
        CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

        OptionParserResult optionParserResult = commandLineInterfaceResult.getOptionParserResultGlobal();
        assertFalse(optionParserResult.isEmpty());
        assertEquals(optionParserResult.getSize(), 1);
        assertTrue(optionParserResult.hasOption("DEBUG"));

        List<String> commandList = commandLineInterfaceResult.getCommandParserResult().getCommandStringList();
        assertEquals(1, commandList.size());
        assertEquals("addUser", commandList.get(0));

        OptionParserResult optionParserResultSpecific = commandLineInterfaceResult.getOptionParserResultSpecific();
        assertFalse(optionParserResultSpecific.isEmpty());
        assertEquals(2, optionParserResultSpecific.getSize());
        assertTrue(optionParserResultSpecific.hasOption("USERNAME"));
        assertEquals("dummy", optionParserResultSpecific.getValue("USERNAME"));
        assertTrue(optionParserResultSpecific.hasOption("PASSWORD"));
        assertEquals("topsecret", optionParserResultSpecific.getValue("PASSWORD"));
    }

    @Test
    void specificOptionForOtherCommand() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"deleteUser", "--username", "dummy", "--doit"};

        CommandLineInterfaceCall commandLineInterfaceCall = commandLineInterface.parse(args);
        CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

        OptionParserResult optionParserResult = commandLineInterfaceResult.getOptionParserResultGlobal();
        assertTrue(optionParserResult.isEmpty());

        List<String> commandList = commandLineInterfaceResult.getCommandParserResult().getCommandStringList();
        assertEquals(1, commandList.size());
        assertEquals("deleteUser", commandList.get(0));

        OptionParserResult optionParserResultSpecific = commandLineInterfaceResult.getOptionParserResultSpecific();
        assertFalse(optionParserResultSpecific.isEmpty());
        assertEquals(2, optionParserResultSpecific.getSize());
        assertTrue(optionParserResultSpecific.hasOption("USERNAME"));
        assertEquals("dummy", optionParserResultSpecific.getValue("USERNAME"));
        assertTrue(optionParserResultSpecific.hasOption("DOIT"));
    }

}