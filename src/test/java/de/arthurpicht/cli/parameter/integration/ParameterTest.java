package de.arthurpicht.cli.parameter.integration;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommandBuilder;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.ParametersMin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParameterTest {

    private Cli createCli() {
        Options globalOptions = new Options()
                .add(new OptionBuilder().withShortName('v').withLongName("v").withDescription("verbose").build("v"))
                .add(new OptionBuilder().withShortName('o').withLongName("option").hasArgument().build("option"));

        Commands commands = new Commands().setDefaultCommand(
                new DefaultCommandBuilder().withParameters(new ParametersMin(0)).build()
        );

        return new CliBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .build("test");
    }

    @Test
    void noParameter() {
        Cli cli = createCli();

        String[] args = {"-v"};

        try {
            CliCall cliCall = cli.parse(args);
            CliResult cliResult = cliCall.getCliResult();

            assertEquals(1, cliResult.getOptionParserResultGlobal().getSize());
            assertTrue(cliResult.getOptionParserResultGlobal().hasOption("v"));

            assertTrue(cliCall.getCommandList().isEmpty());
            assertTrue(cliResult.getOptionParserResultSpecific().isEmpty());

            assertEquals(0, cliResult.getParameterParserResult().getNrOfParameters());

        } catch (UnrecognizedArgumentException e) {
            TestOut.printStacktrace(e);
            TestOut.println(e.getMessage());
            TestOut.println(e.getArgumentIndex());
            fail(e);
        }
    }


    @Test
    void parameterOnly() {
        Cli cli = createCli();

        String[] args = {"myArg"};

        try {
            CliCall cliCall = cli.parse(args);
            CliResult cliResult = cliCall.getCliResult();

            assertTrue(cliResult.getOptionParserResultGlobal().isEmpty());
            assertTrue(cliCall.getCommandList().isEmpty());
            assertTrue(cliResult.getOptionParserResultSpecific().isEmpty());

            assertEquals(1, cliResult.getParameterParserResult().getNrOfParameters());
            assertEquals("myArg", cliResult.getParameterParserResult().getParameterList().get(0));

        } catch (UnrecognizedArgumentException e) {
            TestOut.printStacktrace(e);
            TestOut.println(e.getMessage());
            TestOut.println(e.getArgumentIndex());
            fail(e);
        }
    }

    @Test
    void oneOptionAndParameter() {

        Cli cli = createCli();

        String[] args = {"-v", "myArg"};

        try {
            CliCall cliCall = cli.parse(args);
            CliResult cliResult = cliCall.getCliResult();

            assertEquals(1, cliResult.getOptionParserResultGlobal().getSize());
            assertTrue(cliResult.getOptionParserResultGlobal().hasOption("v"));

            assertTrue(cliCall.getCommandList().isEmpty());
            assertTrue(cliResult.getOptionParserResultSpecific().isEmpty());

            assertEquals(1, cliResult.getParameterParserResult().getNrOfParameters());
            assertEquals("myArg", cliResult.getParameterParserResult().getParameterList().get(0));

        } catch (UnrecognizedArgumentException e) {
            TestOut.printStacktrace(e);
            TestOut.println(e.getMessage());
            TestOut.println(e.getArgumentIndex());
            fail(e);
        }
    }

    @Test
    void oneOptionWithArgumentAndParameter() {

        Cli cli = createCli();

        String[] args = {"-o", "myArg", "myPara"};

        try {
            CliCall cliCall = cli.parse(args);
            CliResult cliResult = cliCall.getCliResult();

            assertEquals(1, cliResult.getOptionParserResultGlobal().getSize());
            assertTrue(cliResult.getOptionParserResultGlobal().hasOption("option"));
            assertEquals("myArg", cliResult.getOptionParserResultGlobal().getValue("option"));

            assertTrue(cliCall.getCommandList().isEmpty());
            assertTrue(cliResult.getOptionParserResultSpecific().isEmpty());

            assertEquals(1, cliResult.getParameterParserResult().getNrOfParameters());
            assertEquals("myPara", cliResult.getParameterParserResult().getParameterList().get(0));

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getArgumentIndex());
            fail();
        }
    }


}
