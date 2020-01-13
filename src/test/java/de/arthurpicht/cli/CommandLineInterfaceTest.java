package de.arthurpicht.cli;

import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.cli.parameter.ParametersVar;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.Option;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.option.Options;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandLineInterfaceTest {

    @Test
    void optionsGlobalArgs() {

        Options optionsGlobal = new Options()
                .add(new Option("idA", 'a', "aaa", true, "", "aaa help"))
                .add(new Option("idB", 'b', "bbb", true,"" , "bbb help"));

        Parameters parameters = new ParametersVar(0);

        CommandLineInterface commandLineInterface = new CommandLineInterface(optionsGlobal, null, null, parameters);

        String[] args = {"-a", "valueOfA", "-b", "valueOfB", "arg1"};

        try {
            ParserResult parserResult = commandLineInterface.parse(args);

            assertNotNull(parserResult.getOptionParserResultGlobal());
            OptionParserResult optionParserResultGlobal = parserResult.getOptionParserResultGlobal();

            assertEquals(2, optionParserResultGlobal.getSize());

            assertTrue(optionParserResultGlobal.hasOption("idA"));
            assertEquals("valueOfA", optionParserResultGlobal.getValue("idA"));
            assertTrue(optionParserResultGlobal.hasOption("idB"));
            assertEquals("valueOfB", optionParserResultGlobal.getValue("idB"));

            List<String> argumentList = parserResult.getArgumentList();
            assertEquals(1, argumentList.size());
            assertEquals("arg1", argumentList.get(0));

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void optionsGlobalArgs2() {

        Options optionsGlobal = new Options()
                .add(new Option("idA", 'a', "aaa", true,"" , "aaa help"))
                .add(new Option("idB", 'b', "bbb", true,"" , "bbb help"));

        Parameters parameters = new ParametersVar(0);

        CommandLineInterface commandLineInterface = new CommandLineInterface(optionsGlobal, null, null, parameters);

        String[] args = {"arg1"};

        try {
            ParserResult parserResult = commandLineInterface.parse(args);

            assertNotNull(parserResult.getOptionParserResultGlobal());
            OptionParserResult optionParserResultGlobal = parserResult.getOptionParserResultGlobal();

            assertEquals(0, optionParserResultGlobal.getSize());

            List<String> argumentList = parserResult.getArgumentList();
            assertEquals(1, argumentList.size());
            assertEquals("arg1", argumentList.get(0));

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    void optionsGlobalCommandsArgs() {

        Options optionsGlobal = new Options()
                .add(new Option("idA", 'a', "aaa", true,"" , "aaa help"))
                .add(new Option("idB", 'b', "bbb", true,"" , "bbb help"));

        Commands commands = new Commands();
        commands.add("commandA").add("commandB");

        Parameters parameters = new ParametersVar(0);

        CommandLineInterface commandLineInterface = new CommandLineInterface(optionsGlobal, commands, null, parameters);

        String[] args = {"-a", "valueOfA", "-b", "valueOfB", "commandA", "commandB", "arg1"};

        try {
            ParserResult parserResult = commandLineInterface.parse(args);

            assertNotNull(parserResult.getOptionParserResultGlobal());
            OptionParserResult optionParserResultGlobal = parserResult.getOptionParserResultGlobal();

            assertEquals(2, optionParserResultGlobal.getSize());

            assertTrue(optionParserResultGlobal.hasOption("idA"));
            assertEquals("valueOfA", optionParserResultGlobal.getValue("idA"));
            assertTrue(optionParserResultGlobal.hasOption("idB"));
            assertEquals("valueOfB", optionParserResultGlobal.getValue("idB"));

            List<String> commandList = parserResult.getCommandList();
            assertEquals(2, commandList.size(), "number of commands");
            assertEquals("commandA", commandList.get(0));
            assertEquals("commandB", commandList.get(1));

            List<String> argumentList = parserResult.getArgumentList();
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
        commands.add("commandA").add("commandB");

        Options optionsSpecific = new Options()
                .add(new Option("idC", 'c', "ccc", true,"" , "ccc help"))
                .add(new Option("idD", 'd', "ddd", true,"" , "ddd help"));

        Parameters parameters = new ParametersVar(0);

        CommandLineInterface commandLineInterface = new CommandLineInterface(optionsGlobal, commands, optionsSpecific, parameters);

        String[] args = {"-a", "valueOfA", "-b", "valueOfB", "commandA", "commandB", "--ccc", "valueOfC", "arg1"};

        try {
            ParserResult parserResult = commandLineInterface.parse(args);

            // global options

            assertNotNull(parserResult.getOptionParserResultGlobal());
            OptionParserResult optionParserResultGlobal = parserResult.getOptionParserResultGlobal();

            assertEquals(2, optionParserResultGlobal.getSize());

            assertTrue(optionParserResultGlobal.hasOption("idA"));
            assertEquals("valueOfA", optionParserResultGlobal.getValue("idA"));
            assertTrue(optionParserResultGlobal.hasOption("idB"));
            assertEquals("valueOfB", optionParserResultGlobal.getValue("idB"));

            // commands

            List<String> commandList = parserResult.getCommandList();
            assertEquals(2, commandList.size(), "number of commands");
            assertEquals("commandA", commandList.get(0));
            assertEquals("commandB", commandList.get(1));

            // specific options

            assertNotNull(parserResult.getOptionParserResultSpecific());
            OptionParserResult optionParserResultSpecific = parserResult.getOptionParserResultSpecific();

            assertEquals(1, optionParserResultSpecific.getSize());

            assertTrue(optionParserResultSpecific.hasOption("idC"));
            assertEquals("valueOfC", optionParserResultSpecific.getValue("idC"));

            // arguments

            List<String> argumentList = parserResult.getArgumentList();
            assertEquals(1, argumentList.size());
            assertEquals("arg1", argumentList.get(0));

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail();
        }
    }

}