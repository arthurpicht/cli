package de.arthurpicht.cli.integration.demo;

import de.arthurpicht.cli.Cli;
import de.arthurpicht.cli.CliBuilder;
import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CliResult;
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

    private Cli createCli() {

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

        return new CliBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .build("test");
    }

    @Test
    void mostSimple() throws UnrecognizedArgumentException {

        Cli cli = createCli();

        String[] args = {"addUser"};

        CliCall cliCall = cli.parse(args);
        CliResult cliResult = cliCall.getCliResult();

        assertTrue(cliResult.getOptionParserResultGlobal().isEmpty());

        List<String> commandList = cliResult.getCommandParserResult().getCommandStringList();
        assertEquals(1, commandList.size());
        assertEquals("addUser", commandList.get(0));

        assertTrue(cliResult.getOptionParserResultSpecific().isEmpty());
    }

    @Test
    void globalOptions() throws UnrecognizedArgumentException {

        Cli cli = createCli();

        String[] args = {"-d", "addUser"};

        CliCall cliCall = cli.parse(args);
        CliResult cliResult = cliCall.getCliResult();

        OptionParserResult optionParserResult = cliResult.getOptionParserResultGlobal();
        assertFalse(optionParserResult.isEmpty());
        assertEquals(optionParserResult.getSize(), 1);
        assertTrue(optionParserResult.hasOption("DEBUG"));

        List<String> commandList = cliResult.getCommandParserResult().getCommandStringList();
        assertEquals(1, commandList.size());
        assertEquals("addUser", commandList.get(0));

        assertTrue(cliResult.getOptionParserResultSpecific().isEmpty());
    }

    @Test
    void specificOptionForFirstCommand() throws UnrecognizedArgumentException {

        Cli cli = createCli();

        String[] args = {"-d", "addUser", "--username", "dummy", "--password", "topsecret"};

        CliCall cliCall = cli.parse(args);
        CliResult cliResult = cliCall.getCliResult();

        OptionParserResult optionParserResult = cliResult.getOptionParserResultGlobal();
        assertFalse(optionParserResult.isEmpty());
        assertEquals(optionParserResult.getSize(), 1);
        assertTrue(optionParserResult.hasOption("DEBUG"));

        List<String> commandList = cliResult.getCommandParserResult().getCommandStringList();
        assertEquals(1, commandList.size());
        assertEquals("addUser", commandList.get(0));

        OptionParserResult optionParserResultSpecific = cliResult.getOptionParserResultSpecific();
        assertFalse(optionParserResultSpecific.isEmpty());
        assertEquals(2, optionParserResultSpecific.getSize());
        assertTrue(optionParserResultSpecific.hasOption("USERNAME"));
        assertEquals("dummy", optionParserResultSpecific.getValue("USERNAME"));
        assertTrue(optionParserResultSpecific.hasOption("PASSWORD"));
        assertEquals("topsecret", optionParserResultSpecific.getValue("PASSWORD"));
    }

    @Test
    void specificOptionForOtherCommand() throws UnrecognizedArgumentException {

        Cli cli = createCli();

        String[] args = {"deleteUser", "--username", "dummy", "--doit"};

        CliCall cliCall = cli.parse(args);
        CliResult cliResult = cliCall.getCliResult();

        OptionParserResult optionParserResult = cliResult.getOptionParserResultGlobal();
        assertTrue(optionParserResult.isEmpty());

        List<String> commandList = cliResult.getCommandParserResult().getCommandStringList();
        assertEquals(1, commandList.size());
        assertEquals("deleteUser", commandList.get(0));

        OptionParserResult optionParserResultSpecific = cliResult.getOptionParserResultSpecific();
        assertFalse(optionParserResultSpecific.isEmpty());
        assertEquals(2, optionParserResultSpecific.getSize());
        assertTrue(optionParserResultSpecific.hasOption("USERNAME"));
        assertEquals("dummy", optionParserResultSpecific.getValue("USERNAME"));
        assertTrue(optionParserResultSpecific.hasOption("DOIT"));
    }

}