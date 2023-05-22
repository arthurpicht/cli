package de.arthurpicht.cli.integration;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.console.Console;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutoHelpTest {

    private Cli createCli() {

        Commands commands = new Commands()
                .add(new CommandSequenceBuilder().addCommands("A", "B").withDescription("The B command.").build())
                .add(new CommandSequenceBuilder().addCommands("A", "C").withDescription("The C command.").build());

        return new CliBuilder()
                .withCommands(commands)
                .withAutoHelp()
                .build("test");
    }

    @Test
    public void global() throws CommandExecutorException, UnrecognizedArgumentException {

        PrintTestContext printTestContext = new PrintTestContext();
        printTestContext.configureConsole();

        Cli cli = createCli();
        String[] args = {"-h"};
        cli.execute(args);

        String output = printTestContext.getOutput();
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

        PrintTestContext printTestContext = new PrintTestContext();
        printTestContext.configureConsole();

        Cli cli = createCli();
        String[] args = {"A", "B", "-h"};
        cli.execute(args);

        String output = printTestContext.getOutput();
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
