package de.arthurpicht.cli.integration;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.DefaultCommandBuilder;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.*;
import de.arthurpicht.cli.parameter.ParametersMin;
import de.arthurpicht.cli.parameter.ParametersOne;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpTextTest {

    private CommandLineInterface getCommandLineInterface(PrintStream out) {

        Options globalOptions = new Options()
                .add(new VersionOption())
                .add(new HelpOption())
                .add(new ManOption())
                .add(new OptionBuilder().withLongName("stacktrace").withDescription("Show stacktrace on error occurrence.").build("STACKTRACE"))
                .add(new OptionBuilder().withLongName("loglevel").withArgumentName("loglevel").withDescription("Log level.").build("LOGLEVEL"));

        DefaultCommand defaultCommand = new DefaultCommandBuilder()
                .withParameters(new ParametersOne())
                .withDescription("The default command")
                .build();

        Commands commands = new Commands();
        commands.setDefaultCommand(defaultCommand);
        commands.add(
                new CommandSequenceBuilder()
                        .addCommands("COMMAND_A")
                        .withSpecificOptions(
                                new Options()
                                        .add(new HelpOption())
                                        .add(new Option("A", 'A', "almost-all", false, "", "do not list implied . and .."))
                        )
                        .withParameters(new ParametersMin(1, "file", "Files to be processed."))
                        .withDescription("This is a description for command_A.")
                        .build()
        );
        commands.add(
                new CommandSequenceBuilder()
                        .addCommands("COMMAND_B")
                        .withSpecificOptions(
                                new Options()
                                .add(new HelpOption())
                                .add(new Option("B", 'b', "brrr", false, "", "The brrr option"))
                                .add(new Option("C", 'c', "cool", true, "what", "The cool option."))
                        )
                        .withParameters(new ParametersOne())
                        .build()
        );

        CommandLineInterfaceDescription commandLineInterfaceDescription
                = new CommandLineInterfaceDescriptionBuilder("test")
                .withDescription("A description for test.")
                .withVersion("v1.0.0")
                .withDate("18.02.2021")
                .build();

        return new CommandLineInterfaceBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .withOut(out)
                .build(commandLineInterfaceDescription);
    }

    @Test
    public void specificHelp() throws CommandExecutorException, UnrecognizedArgumentException {

        ByteArrayOutputStream outBAOS = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outBAOS);

        CommandLineInterface commandLineInterface = getCommandLineInterface(out);
        String[] args = {"COMMAND_A", "-h"};
        commandLineInterface.execute(args);

        String output = outBAOS.toString();
        TestOut.println(output);

        String expectedOutput =
                "Usage: test [global options] COMMAND_A [specific options] 1...n*<file>\n" +
                        "  This is a description for command_A.\n" +
                        "Global Options:\n" +
                        "  -h, --help                    Show help message and exit.\n" +
                        "      --loglevel <loglevel>     Log level.\n" +
                        "  -m, --man                     Show manual and exit.\n" +
                        "      --stacktrace              Show stacktrace on error occurrence.\n" +
                        "  -v, --version                 Show version message and exit.\n" +
                        "Specific options:\n" +
                        "  -A, --almost-all              do not list implied . and ..\n" +
                        "  -h, --help                    Show help message and exit.\n" +
                        "Parameters:\n" +
                        "  1...n*<file>                  Files to be processed.\n";

        assertEquals(expectedOutput, output);
    }

    @Test
    public void globalHelp() throws CommandExecutorException, UnrecognizedArgumentException {

        ByteArrayOutputStream outBAOS = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outBAOS);

        CommandLineInterface commandLineInterface = getCommandLineInterface(out);
        String[] args = {"-h"};
        commandLineInterface.execute(args);

        String output = outBAOS.toString();
        TestOut.println(output);

        String expectedOutput = "test v1.0.0 from 18.02.2021\n" +
                "  A description for test.\n" +
                "Usage:\n" +
                "  test [global options] <parameter>\n" +
                "  test [global options] COMMAND_A [specific options] 1...n*<file>\n" +
                "  test [global options] COMMAND_B [specific options] <parameter>\n" +
                "Global Options:\n" +
                "  -h, --help                    Show help message and exit.\n" +
                "      --loglevel <loglevel>     Log level.\n" +
                "  -m, --man                     Show manual and exit.\n" +
                "      --stacktrace              Show stacktrace on error occurrence.\n" +
                "  -v, --version                 Show version message and exit.\n";

        assertEquals(expectedOutput, output);
    }

    @Test
    public void man() throws CommandExecutorException, UnrecognizedArgumentException {

        ByteArrayOutputStream outBAOS = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outBAOS);

        CommandLineInterface commandLineInterface = getCommandLineInterface(out);
        String[] args = {"-m"};
        commandLineInterface.execute(args);

        String output = outBAOS.toString();

        System.out.println(output);

        String expectedOutput =
                "";


//        String expectedOutput = "test v1.0.0 from 18.02.2021\n" +
//                "  A description for test.\n" +
//                "Usage:\n" +
//                "  test [global options] <parameter>\n" +
//                "  test [global options] COMMAND_A [specific options] 1...n*<file>\n" +
//                "  test [global options] COMMAND_B [specific options] <parameter>\n" +
//                "Global Options:\n" +
//                "  -h, --help                    Show help message and exit.\n" +
//                "      --loglevel <loglevel>     Log level.\n" +
//                "      --stacktrace              Show stacktrace on error occurrence.\n" +
//                "  -v, --version                 Show version message and exit.\n";
//
//        assertEquals(expectedOutput, output);
    }


}
