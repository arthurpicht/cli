package de.arthurpicht.cli.integration;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.console.Console;
import de.arthurpicht.console.config.ConsoleConfigurationBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutoHelpTest {

    private Cli createCli(PrintStream out) {

        Commands commands = new Commands()
                .add(new CommandSequenceBuilder().addCommands("A", "B").withDescription("The B command.").build())
                .add(new CommandSequenceBuilder().addCommands("A", "C").withDescription("The C command.").build());

        return new CliBuilder()
                .withCommands(commands)
                .withAutoHelp()
                .withOut(out)
                .build("test");
    }

    @Test
    public void global() throws CommandExecutorException, UnrecognizedArgumentException {

        ByteArrayOutputStream outBAOS = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outBAOS);

        Console.init(new ConsoleConfigurationBuilder()
                .withStandardOut(out)
                .withSuppressedColors()
                .build());

        Cli cli = createCli(out);
        String[] args = {"-h"};
        cli.execute(args);

        String output = outBAOS.toString();
        TestOut.println(output);

        String expectedOutput = "test\n" +
                "Usage:\n" +
                "  test [global options] A B [specific options]\n" +
                "  test [global options] A C [specific options]\n" +
                "Global Options:\n" +
                "  -h, --help                    Show help message and exit.\n";

        assertEquals(expectedOutput, output);

        Console.initWithDefaults();
    }

    @Test
    public void specificB() throws CommandExecutorException, UnrecognizedArgumentException {

        ByteArrayOutputStream outBAOS = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outBAOS);

        Cli cli = createCli(out);
        String[] args = {"A", "B", "-h"};
        cli.execute(args);

        String output = outBAOS.toString();
        TestOut.println(output);

        String expectedOutput = "Usage: test [global options] A B [specific options]\n" +
                "  The B command.\n" +
                "Global Options:\n" +
                "  -h, --help                    Show help message and exit.\n" +
                "Specific options:\n" +
                "  -h, --help                    Show help message and exit.\n";

        assertEquals(expectedOutput, output);
    }

}
