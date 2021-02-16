package de.arthurpicht.cli.parameter.integration;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommandBuilder;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.ParametersVar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParameterTest {

    private CommandLineInterface getCommandLineInterface() {
        Options globalOptions = new Options()
                .add(new OptionBuilder().withShortName('v').withLongName("v").withDescription("verbose").build("v"))
                .add(new OptionBuilder().withShortName('o').withLongName("option").hasArgument().build("option"));

        Commands commands = new Commands().setDefaultCommand(
                new DefaultCommandBuilder().withParameters(new ParametersVar(0)).build()
        );

        return new CommandLineInterfaceBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .build("test");
    }

    @Test
    void noParameter() {
        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"-v"};

        try {
            CommandLineInterfaceCall commandLineInterfaceCall = commandLineInterface.parse(args);
            CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

            assertEquals(1, commandLineInterfaceResult.getOptionParserResultGlobal().getSize());
            assertTrue(commandLineInterfaceResult.getOptionParserResultGlobal().hasOption("v"));

            assertTrue(commandLineInterfaceCall.getCommandList().isEmpty());
            assertTrue(commandLineInterfaceResult.getOptionParserResultSpecific().isEmpty());

            assertEquals(0, commandLineInterfaceResult.getParameterParserResult().getNrOfParameters());

        } catch (UnrecognizedArgumentException e) {
            TestOut.printStacktrace(e);
            TestOut.println(e.getMessage());
            TestOut.println(e.getArgumentIndex());
            fail(e);
        }
    }


    @Test
    void parameterOnly() {
        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"myArg"};

        try {
            CommandLineInterfaceCall commandLineInterfaceCall = commandLineInterface.parse(args);
            CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

            assertTrue(commandLineInterfaceResult.getOptionParserResultGlobal().isEmpty());
            assertTrue(commandLineInterfaceCall.getCommandList().isEmpty());
            assertTrue(commandLineInterfaceResult.getOptionParserResultSpecific().isEmpty());

            assertEquals(1, commandLineInterfaceResult.getParameterParserResult().getNrOfParameters());
            assertEquals("myArg", commandLineInterfaceResult.getParameterParserResult().getParameterList().get(0));

        } catch (UnrecognizedArgumentException e) {
            TestOut.printStacktrace(e);
            TestOut.println(e.getMessage());
            TestOut.println(e.getArgumentIndex());
            fail(e);
        }
    }

    @Test
    void oneOptionAndParameter() {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"-v", "myArg"};

        try {
            CommandLineInterfaceCall commandLineInterfaceCall = commandLineInterface.parse(args);
            CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

            assertEquals(1, commandLineInterfaceResult.getOptionParserResultGlobal().getSize());
            assertTrue(commandLineInterfaceResult.getOptionParserResultGlobal().hasOption("v"));

            assertTrue(commandLineInterfaceCall.getCommandList().isEmpty());
            assertTrue(commandLineInterfaceResult.getOptionParserResultSpecific().isEmpty());

            assertEquals(1, commandLineInterfaceResult.getParameterParserResult().getNrOfParameters());
            assertEquals("myArg", commandLineInterfaceResult.getParameterParserResult().getParameterList().get(0));

        } catch (UnrecognizedArgumentException e) {
            TestOut.printStacktrace(e);
            TestOut.println(e.getMessage());
            TestOut.println(e.getArgumentIndex());
            fail(e);
        }
    }

    @Test
    void oneOptionWithArgumentAndParameter() {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"-o", "myArg", "myPara"};

        try {
            CommandLineInterfaceCall commandLineInterfaceCall = commandLineInterface.parse(args);
            CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

            assertEquals(1, commandLineInterfaceResult.getOptionParserResultGlobal().getSize());
            assertTrue(commandLineInterfaceResult.getOptionParserResultGlobal().hasOption("option"));
            assertEquals("myArg", commandLineInterfaceResult.getOptionParserResultGlobal().getValue("option"));

            assertTrue(commandLineInterfaceCall.getCommandList().isEmpty());
            assertTrue(commandLineInterfaceResult.getOptionParserResultSpecific().isEmpty());

            assertEquals(1, commandLineInterfaceResult.getParameterParserResult().getNrOfParameters());
            assertEquals("myPara", commandLineInterfaceResult.getParameterParserResult().getParameterList().get(0));

//            commandLineInterfaceCall.debugOut();

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getArgumentIndex());
            fail();
        }
    }


}
