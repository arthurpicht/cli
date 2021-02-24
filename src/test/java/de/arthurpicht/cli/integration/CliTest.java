package de.arthurpicht.cli.integration;

import de.arthurpicht.cli.Cli;
import de.arthurpicht.cli.CliBuilder;
import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.Option;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.cli.parameter.ParametersMin;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CliTest {

    @Test
    void globalOptionsDefaultCommandWithParameter() throws UnrecognizedArgumentException {

        Options optionsGlobal = new Options()
                .add(new Option("idA", 'a', "aaa", true, "", "aaa help"))
                .add(new Option("idB", 'b', "bbb", true,"" , "bbb help"));

        Commands commands = new Commands();
        Parameters parameters = new ParametersMin(0);
        commands.setDefaultCommand(new DefaultCommand(parameters, null, "Test command."));

        Cli cli = new CliBuilder()
                .withGlobalOptions(optionsGlobal)
                .withCommands(commands)
                .build("test");

        String[] args = {"-a", "valueOfA", "-b", "valueOfB", "arg1"};

        CliCall cliCall = cli.parse(args);

        assertNotNull(cliCall.getOptionParserResultGlobal());
        OptionParserResult optionParserResultGlobal = cliCall.getOptionParserResultGlobal();

        assertEquals(2, optionParserResultGlobal.getSize());

        assertTrue(optionParserResultGlobal.hasOption("idA"));
        assertEquals("valueOfA", optionParserResultGlobal.getValue("idA"));
        assertTrue(optionParserResultGlobal.hasOption("idB"));
        assertEquals("valueOfB", optionParserResultGlobal.getValue("idB"));

        List<String> argumentList = cliCall.getCliResult().getParameterParserResult().getParameterList();
        assertEquals(1, argumentList.size());
        assertEquals("arg1", argumentList.get(0));
    }

    @Test
    void globalOptionsDefaultCommandParameterOnly() {

        Options optionsGlobal = new Options()
                .add(new Option("idA", 'a', "aaa", true, "", "aaa help"))
                .add(new Option("idB", 'b', "bbb", true,"" , "bbb help"));

        Commands commands = new Commands();
        Parameters parameters = new ParametersMin(0);
        commands.setDefaultCommand(new DefaultCommand(parameters, null, "Test command."));

        Cli cli = new CliBuilder()
                .withGlobalOptions(optionsGlobal)
                .withCommands(commands)
                .build("test");

        String[] args = {"arg1"};

        try {
            CliCall cliCall = cli.parse(args);

            assertNotNull(cliCall.getOptionParserResultGlobal());
            OptionParserResult optionParserResultGlobal = cliCall.getOptionParserResultGlobal();

            assertEquals(0, optionParserResultGlobal.getSize());

            List<String> argumentList = cliCall.getCliResult().getParameterParserResult().getParameterList();
            assertEquals(1, argumentList.size());
            assertEquals("arg1", argumentList.get(0));

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    void globalOptionsCommandsParameter() {

        Options optionsGlobal = new Options()
                .add(new Option("idA", 'a', "aaa", true, "", "aaa help"))
                .add(new Option("idB", 'b', "bbb", true,"" , "bbb help"));

        Commands commands = new Commands();
        Parameters parameters = new ParametersMin(0);
        commands.add(
                new CommandSequenceBuilder()
                        .addCommands("commandA", "commandB")
                        .withParameters(parameters)
                        .build()
        );

        Cli cli = new CliBuilder()
                .withGlobalOptions(optionsGlobal)
                .withCommands(commands)
                .build("test");

        String[] args = {"-a", "valueOfA", "-b", "valueOfB", "commandA", "commandB", "arg1"};

        try {
            CliCall cliCall = cli.parse(args);

            assertNotNull(cliCall.getOptionParserResultGlobal());
            OptionParserResult optionParserResultGlobal = cliCall.getOptionParserResultGlobal();

            assertEquals(2, optionParserResultGlobal.getSize());

            assertTrue(optionParserResultGlobal.hasOption("idA"));
            assertEquals("valueOfA", optionParserResultGlobal.getValue("idA"));
            assertTrue(optionParserResultGlobal.hasOption("idB"));
            assertEquals("valueOfB", optionParserResultGlobal.getValue("idB"));

            List<String> commandList = cliCall.getCommandList();
            assertEquals(2, commandList.size(), "number of commands");
            assertEquals("commandA", commandList.get(0));
            assertEquals("commandB", commandList.get(1));

            List<String> argumentList = cliCall.getCliResult().getParameterParserResult().getParameterList();
            assertEquals(1, argumentList.size());
            assertEquals("arg1", argumentList.get(0));

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void full() {

        Options optionsGlobal = new Options()
                .add(new Option("idA", 'a', "aaa", true,"" , "aaa help"))
                .add(new Option("idB", 'b', "bbb", true,"" , "bbb help"));

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder()
                .addCommand("commandA")
                .withParameters(new ParametersMin(0))
                .build());

        commands.add(new CommandSequenceBuilder()
                .addCommands("commandA", "commandB")
                .withSpecificOptions(new Options()
                        .add(new Option("idC", 'c', "ccc", true, "", "ccc help"))
                        .add(new Option("idD", 'd', "ddd", true, "", "ddd help"))
                        )
                .withParameters(new ParametersMin(0))
                .build()
        );

        Cli cli = new CliBuilder()
                .withGlobalOptions(optionsGlobal)
                .withCommands(commands)
                .build("test");

        String[] args = {"-a", "valueOfA", "-b", "valueOfB", "commandA", "commandB", "--ccc", "valueOfC", "arg1"};

        try {
            CliCall cliCall = cli.parse(args);

            // global options

            assertNotNull(cliCall.getOptionParserResultGlobal());
            OptionParserResult optionParserResultGlobal = cliCall.getOptionParserResultGlobal();

            assertEquals(2, optionParserResultGlobal.getSize());

            assertTrue(optionParserResultGlobal.hasOption("idA"));
            assertEquals("valueOfA", optionParserResultGlobal.getValue("idA"));
            assertTrue(optionParserResultGlobal.hasOption("idB"));
            assertEquals("valueOfB", optionParserResultGlobal.getValue("idB"));

            // commands

            List<String> commandList = cliCall.getCommandList();
            assertEquals(2, commandList.size(), "number of commands");
            assertEquals("commandA", commandList.get(0));
            assertEquals("commandB", commandList.get(1));

            // specific options

            assertNotNull(cliCall.getOptionParserResultSpecific());
            OptionParserResult optionParserResultSpecific = cliCall.getOptionParserResultSpecific();

            assertEquals(1, optionParserResultSpecific.getSize());

            assertTrue(optionParserResultSpecific.hasOption("idC"));
            assertEquals("valueOfC", optionParserResultSpecific.getValue("idC"));

            // arguments

            List<String> argumentList = cliCall.getCliResult().getParameterParserResult().getParameterList();
            assertEquals(1, argumentList.size());
            assertEquals("arg1", argumentList.get(0));

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail();
        }
    }

}