package de.arthurpicht.cli.integration.demo;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.DefaultCommandBuilder;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.HelpOption;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.ParametersNBuilder;
import de.arthurpicht.console.Console;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DemoMvCommand {

    private Cli createCli() {

        Options globalOptions = new Options().add(new HelpOption());

        DefaultCommand defaultCommand =
                new DefaultCommandBuilder()
                        .withParameters(
                                new ParametersNBuilder()
                                        .addParameter("source", "file to be moved")
                                        .addParameter("destination", "destination file to be moved to")
                                        .build())
                        .withCommandExecutor(
                                (cliCall)
                                        -> TestOut.println(
                                        "Copy file from " + cliCall.getParameterList().get(0) + " to "
                                                + cliCall.getParameterList().get(1))
                        ).build();

        Commands commands = new Commands().setDefaultCommand(defaultCommand);

        CliDescription cliDescription =
                new CliDescriptionBuilder()
                .withDescription("(Demo) Move file from source to destination.")
                .withVersionByTag("1.0", "22.02.2021")
                .build("mv");

        return new CliBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .build(cliDescription);
    }

    @Test
    public void regularCall() throws UnrecognizedArgumentException, CommandExecutorException {

        Console.initWithDefaults();

        Cli cli = createCli();

        String[] args = {"source", "destination"};

        CliCall call = cli.execute(args);
        CliResult result = call.getCliResult();

        assertTrue(result.getOptionParserResultGlobal().isEmpty());
        assertTrue(result.getCommandParserResult().getCommandStringList().isEmpty());
        assertTrue(result.getOptionParserResultSpecific().isEmpty());
        assertEquals(2, result.getParameterParserResult().getNrOfParameters());
        assertTrue(result.getParameterParserResult().getParameterList().contains("source"));
        assertTrue(result.getParameterParserResult().getParameterList().contains("destination"));
    }

    @Test
    public void help() throws CommandExecutorException, UnrecognizedArgumentException {

        PrintTestContext printTestContext = new PrintTestContext();
        printTestContext.configureConsole();

        Cli cli = createCli();
        String[] args = {"-h"};

        cli.execute(args);

        String output = printTestContext.getOutput();
        String expectedOutput =
                "mv version 1.0 from 22.02.2021\n" +
                "  (Demo) Move file from source to destination.\n" +
                "Usage:\n" +
                "  mv [options] <source> <destination>\n" +
                "Options:\n" +
                "  -h, --help                    show help message and exit\n" +
                "Parameters:\n" +
                "  <source>                      file to be moved\n" +
                "  <destination>                 destination file to be moved to\n";

        assertEquals(expectedOutput, output);
    }

}
