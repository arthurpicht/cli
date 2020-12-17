package de.arthurpicht.cli.integration.demo;

import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.CommandLineInterfaceBuilder;
import de.arthurpicht.cli.ParserResult;
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
                .add("addUser").withSpecificOptions(specificOptionsAddUser)
                .reset()
                .add("deleteUser").withSpecificOptions(specificOptionsDeleteUser);

        return new CommandLineInterfaceBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .build();
    }

    @Test
    void mostSimple() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"addUser"};

        ParserResult parserResult = commandLineInterface.parse(args);

        assertTrue(parserResult.getOptionParserResultGlobal().isEmpty());

        List<String> commandList = parserResult.getCommandList();
        assertEquals(1, commandList.size());
        assertEquals("addUser", commandList.get(0));

        assertTrue(parserResult.getOptionParserResultSpecific().isEmpty());
    }

    @Test
    void globalOptions() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"-d", "addUser"};

        ParserResult parserResult = commandLineInterface.parse(args);

        OptionParserResult optionParserResult = parserResult.getOptionParserResultGlobal();
        assertFalse(optionParserResult.isEmpty());
        assertEquals(optionParserResult.getSize(), 1);
        assertTrue(optionParserResult.hasOption("DEBUG"));

        List<String> commandList = parserResult.getCommandList();
        assertEquals(1, commandList.size());
        assertEquals("addUser", commandList.get(0));

        assertTrue(parserResult.getOptionParserResultSpecific().isEmpty());
    }

    @Test
    void specificOptionForFirstCommand() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"-d", "addUser", "--username", "dummy", "--password", "topsecret"};

        ParserResult parserResult = commandLineInterface.parse(args);

        OptionParserResult optionParserResult = parserResult.getOptionParserResultGlobal();
        assertFalse(optionParserResult.isEmpty());
        assertEquals(optionParserResult.getSize(), 1);
        assertTrue(optionParserResult.hasOption("DEBUG"));

        List<String> commandList = parserResult.getCommandList();
        assertEquals(1, commandList.size());
        assertEquals("addUser", commandList.get(0));

        OptionParserResult optionParserResultSpecific = parserResult.getOptionParserResultSpecific();
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

        ParserResult parserResult = commandLineInterface.parse(args);

        OptionParserResult optionParserResult = parserResult.getOptionParserResultGlobal();
        assertTrue(optionParserResult.isEmpty());

        List<String> commandList = parserResult.getCommandList();
        assertEquals(1, commandList.size());
        assertEquals("deleteUser", commandList.get(0));

        OptionParserResult optionParserResultSpecific = parserResult.getOptionParserResultSpecific();
        assertFalse(optionParserResultSpecific.isEmpty());
        assertEquals(2, optionParserResultSpecific.getSize());
        assertTrue(optionParserResultSpecific.hasOption("USERNAME"));
        assertEquals("dummy", optionParserResultSpecific.getValue("USERNAME"));
        assertTrue(optionParserResultSpecific.hasOption("DOIT"));
    }

}