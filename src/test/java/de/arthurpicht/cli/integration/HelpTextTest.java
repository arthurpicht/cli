package de.arthurpicht.cli.integration;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.*;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.*;
import de.arthurpicht.cli.parameter.ParametersMin;
import de.arthurpicht.cli.parameter.ParametersOne;
import de.arthurpicht.console.Console;
import de.arthurpicht.console.config.ConsoleConfigurationBuilder;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpTextTest {

    private Cli createCli(boolean withCustomDefaultCommand, boolean withInfoDefaultCommand) {

        if (withCustomDefaultCommand && withInfoDefaultCommand) {
            throw new IllegalArgumentException("Only one one ca be true: 'withCustomDefaultCommand' or 'withInfoDefaultCommand'");
        }

        Options globalOptions = new Options()
                .add(new VersionOption())
                .add(new HelpOption())
                .add(new ManOption())
                .add(new OptionBuilder().withLongName("stacktrace").withDescription("Show stacktrace on error occurrence.").build("STACKTRACE"))
                .add(new OptionBuilder().withLongName("loglevel").withArgumentName("loglevel").withDescription("Log level.").build("LOGLEVEL"));

        Commands commands = new Commands();

        if (withCustomDefaultCommand) {
            DefaultCommand defaultCommand = new DefaultCommandBuilder()
                    .withParameters(new ParametersOne())
                    .withDescription("The default command")
                    .build();
            commands.setDefaultCommand(defaultCommand);
        } else if (withInfoDefaultCommand) {
            DefaultCommand defaultCommand = new InfoDefaultCommand();
            commands.setDefaultCommand(defaultCommand);
        }

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
        //noinspection SpellCheckingInspection
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

        CliDescription cliDescription
                = new CliDescriptionBuilder()
                .withDescription("A description for test.")
                .withVersionByTag("v1.0.0", "18.02.2021")
                .build("test");

        return new CliBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .build(cliDescription);
    }

    @Test
    public void specificHelp() throws CommandExecutorException, UnrecognizedArgumentException {

        PrintTestContext printTestContext = new PrintTestContext();
        printTestContext.configureConsole();

        Cli cli = createCli(true, false);
        String[] args = {"COMMAND_A", "-h"};
        cli.execute(args);

        String output = printTestContext.getOutput();
        TestOut.println(output);

        String expectedOutput =
                "Usage: test [global options] COMMAND_A [specific options] 1..n*<file>\n" +
                        "  This is a description for command_A.\n" +
                        "Global Options:\n" +
                        "  -h, --help                    show help message and exit\n" +
                        "      --loglevel <loglevel>     Log level.\n" +
                        "  -m, --man                     show manual and exit\n" +
                        "      --stacktrace              Show stacktrace on error occurrence.\n" +
                        "  -v, --version                 show version message and exit\n" +
                        "Specific options:\n" +
                        "  -A, --almost-all              do not list implied . and ..\n" +
                        "  -h, --help                    show help message and exit\n" +
                        "Parameters:\n" +
                        "  1..n*<file>                   Files to be processed.\n";

        assertEquals(expectedOutput, output);
    }

    @Test
    public void globalHelpWithCustomDefaultCommand() throws CommandExecutorException, UnrecognizedArgumentException {

        PrintTestContext printTestContext = new PrintTestContext();
        printTestContext.configureConsole();

        Cli cli = createCli(true, false);
        String[] args = {"-h"};
        cli.execute(args);

        String output = printTestContext.getOutput();
        TestOut.println(output);

        String expectedOutput = "test version v1.0.0 from 18.02.2021\n" +
                "  A description for test.\n" +
                "Usage:\n" +
                "  test [global options] <parameter>\n" +
                "  test [global options] COMMAND_A [specific options] 1..n*<file>\n" +
                "  test [global options] COMMAND_B [specific options] <parameter>\n" +
                "Global Options:\n" +
                "  -h, --help                    show help message and exit\n" +
                "      --loglevel <loglevel>     Log level.\n" +
                "  -m, --man                     show manual and exit\n" +
                "      --stacktrace              Show stacktrace on error occurrence.\n" +
                "  -v, --version                 show version message and exit\n";

        assertEquals(expectedOutput, output);

        Console.initWithDefaults();
    }

    @Test
    public void globalHelpWithInfoDefaultCommand() throws CommandExecutorException, UnrecognizedArgumentException {

        PrintTestContext printTestContext = new PrintTestContext();
        printTestContext.configureConsole();

        Cli cli = createCli(false, true);
        String[] args = {"-h"};
        cli.execute(args);

        String output = printTestContext.getOutput();
        TestOut.println(output);

        String expectedOutput = "test version v1.0.0 from 18.02.2021\n" +
                "  A description for test.\n" +
                "Usage:\n" +
                "  test [global options] COMMAND_A [specific options] 1..n*<file>\n" +
                "  test [global options] COMMAND_B [specific options] <parameter>\n" +
                "Global Options:\n" +
                "  -h, --help                    show help message and exit\n" +
                "      --loglevel <loglevel>     Log level.\n" +
                "  -m, --man                     show manual and exit\n" +
                "      --stacktrace              Show stacktrace on error occurrence.\n" +
                "  -v, --version                 show version message and exit\n";

        assertEquals(expectedOutput, output);

        Console.initWithDefaults();
    }

    @Test
    public void globalHelpWithNoDefaultCommand() throws CommandExecutorException, UnrecognizedArgumentException {

        PrintTestContext printTestContext = new PrintTestContext();
        printTestContext.configureConsole();

        Cli cli = createCli(false, false);
        String[] args = {"-h"};
        cli.execute(args);

        String output = printTestContext.getOutput();
        TestOut.println(output);

        String expectedOutput = "test version v1.0.0 from 18.02.2021\n" +
                "  A description for test.\n" +
                "Usage:\n" +
                "  test [global options] COMMAND_A [specific options] 1..n*<file>\n" +
                "  test [global options] COMMAND_B [specific options] <parameter>\n" +
                "Global Options:\n" +
                "  -h, --help                    show help message and exit\n" +
                "      --loglevel <loglevel>     Log level.\n" +
                "  -m, --man                     show manual and exit\n" +
                "      --stacktrace              Show stacktrace on error occurrence.\n" +
                "  -v, --version                 show version message and exit\n";

        assertEquals(expectedOutput, output);

        Console.initWithDefaults();
    }

    @Test
    public void manWithCustomDefaultCommand() throws CommandExecutorException, UnrecognizedArgumentException {

        PrintTestContext printTestContext = new PrintTestContext();
        printTestContext.configureConsole();

        Cli cli = createCli(true, false);
        String[] args = {"-m"};
        cli.execute(args);

        String output = printTestContext.getOutput();

        TestOut.println(output);

        @SuppressWarnings("SpellCheckingInspection")
        String expectedOutput =
                "test version v1.0.0 from 18.02.2021\n" +
                        "  A description for test.\n" +
                        "Usage:\n" +
                        "  test [global options] <parameter>\n" +
                        "  test [global options] COMMAND_A [specific options] 1..n*<file>\n" +
                        "  test [global options] COMMAND_B [specific options] <parameter>\n" +
                        "Global Options:\n" +
                        "  -h, --help                    show help message and exit\n" +
                        "      --loglevel <loglevel>     Log level.\n" +
                        "  -m, --man                     show manual and exit\n" +
                        "      --stacktrace              Show stacktrace on error occurrence.\n" +
                        "  -v, --version                 show version message and exit\n" +
                        "\n" +
                        "Usage: test [global options] <parameter>\n" +
                        "Parameters:\n" +
                        "  <parameter>                   \n" +
                        "\n" +
                        "Usage: test [global options] COMMAND_A [specific options] 1..n*<file>\n" +
                        "  This is a description for command_A.\n" +
                        "Specific options:\n" +
                        "  -A, --almost-all              do not list implied . and ..\n" +
                        "  -h, --help                    show help message and exit\n" +
                        "Parameters:\n" +
                        "  1..n*<file>                   Files to be processed.\n" +
                        "\n" +
                        "Usage: test [global options] COMMAND_B [specific options] <parameter>\n" +
                        "Specific options:\n" +
                        "  -b, --brrr                    The brrr option\n" +
                        "  -c, --cool <what>             The cool option.\n" +
                        "  -h, --help                    show help message and exit\n" +
                        "Parameters:\n" +
                        "  <parameter>                   \n";

        assertEquals(expectedOutput, output);

        Console.initWithDefaults();
    }

    @Test
    public void manWithDefaultInfoCommand() throws CommandExecutorException, UnrecognizedArgumentException {

        PrintTestContext printTestContext = new PrintTestContext();
        printTestContext.configureConsole();

        Cli cli = createCli(false, true);
        String[] args = {"-m"};
        cli.execute(args);

        String output = printTestContext.getOutput();

        TestOut.println(output);

        @SuppressWarnings("SpellCheckingInspection")
        String expectedOutput =
                "test version v1.0.0 from 18.02.2021\n" +
                        "  A description for test.\n" +
                        "Usage:\n" +
                        "  test [global options] COMMAND_A [specific options] 1..n*<file>\n" +
                        "  test [global options] COMMAND_B [specific options] <parameter>\n" +
                        "Global Options:\n" +
                        "  -h, --help                    show help message and exit\n" +
                        "      --loglevel <loglevel>     Log level.\n" +
                        "  -m, --man                     show manual and exit\n" +
                        "      --stacktrace              Show stacktrace on error occurrence.\n" +
                        "  -v, --version                 show version message and exit\n" +
                        "\n" +
                        "Usage: test [global options] COMMAND_A [specific options] 1..n*<file>\n" +
                        "  This is a description for command_A.\n" +
                        "Specific options:\n" +
                        "  -A, --almost-all              do not list implied . and ..\n" +
                        "  -h, --help                    show help message and exit\n" +
                        "Parameters:\n" +
                        "  1..n*<file>                   Files to be processed.\n" +
                        "\n" +
                        "Usage: test [global options] COMMAND_B [specific options] <parameter>\n" +
                        "Specific options:\n" +
                        "  -b, --brrr                    The brrr option\n" +
                        "  -c, --cool <what>             The cool option.\n" +
                        "  -h, --help                    show help message and exit\n" +
                        "Parameters:\n" +
                        "  <parameter>                   \n";

        assertEquals(expectedOutput, output);

        Console.initWithDefaults();
    }

    @Test
    public void manWithNoDefaultCommand() throws CommandExecutorException, UnrecognizedArgumentException {

        PrintTestContext printTestContext = new PrintTestContext();
        printTestContext.configureConsole();

        Cli cli = createCli(false, false);
        String[] args = {"-m"};
        cli.execute(args);

        String output = printTestContext.getOutput();

        TestOut.println(output);

        @SuppressWarnings("SpellCheckingInspection")
        String expectedOutput =
                "test version v1.0.0 from 18.02.2021\n" +
                        "  A description for test.\n" +
                        "Usage:\n" +
                        "  test [global options] COMMAND_A [specific options] 1..n*<file>\n" +
                        "  test [global options] COMMAND_B [specific options] <parameter>\n" +
                        "Global Options:\n" +
                        "  -h, --help                    show help message and exit\n" +
                        "      --loglevel <loglevel>     Log level.\n" +
                        "  -m, --man                     show manual and exit\n" +
                        "      --stacktrace              Show stacktrace on error occurrence.\n" +
                        "  -v, --version                 show version message and exit\n" +
                        "\n" +
                        "Usage: test [global options] COMMAND_A [specific options] 1..n*<file>\n" +
                        "  This is a description for command_A.\n" +
                        "Specific options:\n" +
                        "  -A, --almost-all              do not list implied . and ..\n" +
                        "  -h, --help                    show help message and exit\n" +
                        "Parameters:\n" +
                        "  1..n*<file>                   Files to be processed.\n" +
                        "\n" +
                        "Usage: test [global options] COMMAND_B [specific options] <parameter>\n" +
                        "Specific options:\n" +
                        "  -b, --brrr                    The brrr option\n" +
                        "  -c, --cool <what>             The cool option.\n" +
                        "  -h, --help                    show help message and exit\n" +
                        "Parameters:\n" +
                        "  <parameter>                   \n";

        assertEquals(expectedOutput, output);

        Console.initWithDefaults();
    }

}
