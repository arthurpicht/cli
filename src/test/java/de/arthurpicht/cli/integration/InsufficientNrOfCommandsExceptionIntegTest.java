package de.arthurpicht.cli.integration;

import de.arthurpicht.cli.Cli;
import de.arthurpicht.cli.CliBuilder;
import de.arthurpicht.cli.TestOut;
import de.arthurpicht.cli.command.*;
import de.arthurpicht.cli.command.exceptions.InsufficientNrOfCommandsException;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.HelpOption;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InsufficientNrOfCommandsExceptionIntegTest {

    private Cli createCli() {

        Commands commands = new Commands()
                .add(new CommandSequenceBuilder()
                        .addCommands("A", "B")
                .withSpecificOptions(new Options()
                        .add(new HelpOption())
                        .add(new OptionBuilder().withShortName('x').withLongName("x-test").build("x_test"))
                        .add(new OptionBuilder().withShortName('y').withLongName("y-test").build("y_test"))
                )
                .build());

        return new CliBuilder()
                .withCommands(commands)
                .build("test");
    }

    private Cli createCliWithDefaultCommand() {

        DefaultCommand defaultCommand = new DefaultCommandBuilder()
                .withDescription("Just a dummy default command")
                .build();

        Commands commands = new Commands()
                .add(new CommandSequenceBuilder()
                        .addCommands("A", "B")
                        .withSpecificOptions(new Options()
                                .add(new HelpOption())
                                .add(new OptionBuilder().withShortName('x').withLongName("x-test").build("x_test"))
                                .add(new OptionBuilder().withShortName('y').withLongName("y-test").build("y_test"))
                        )
                        .build())
                .setDefaultCommand(defaultCommand);

        return new CliBuilder()
                .withCommands(commands)
                .build("test");
    }

    @Test
    public void noArgs_neg() {

        Cli cli = createCli();
        String[] args = {};

        try {
            cli.parse(args);
        } catch (UnrecognizedArgumentException e) {

            assertTrue(e instanceof InsufficientNrOfCommandsException);

            TestOut.println(e.getMessage());
            TestOut.println(e.getCallString());
            TestOut.println(e.getCallPointerString());

            assertEquals("Insufficient number of commands. Next command is one of: [ A ].", e.getMessage());
            assertEquals("test", e.getCallString());
            assertEquals("     ^", e.getCallPointerString());
        }
    }

    @Test
    public void optionOnly_neg() {

        Cli cli = createCli();
        String[] args = {"-h"};

        try {
            cli.parse(args);
        } catch (UnrecognizedArgumentException e) {

            assertTrue(e instanceof InsufficientNrOfCommandsException);

            TestOut.println(e.getMessage());
            TestOut.println(e.getCallString());
            TestOut.println(e.getCallPointerString());

            assertEquals("Insufficient number of commands. Next command is one of: [ A ].", e.getMessage());
            assertEquals("test -h", e.getCallString());
            assertEquals("     ^", e.getCallPointerString());
        }
    }

    @Test
    public void optionOnlyWithDefaultCommand_neg() {

        Cli cli = createCliWithDefaultCommand();
        String[] args = {"-h"};

        try {
            cli.parse(args);
        } catch (UnrecognizedArgumentException e) {

            assertTrue(e instanceof InsufficientNrOfCommandsException);

            TestOut.println(e.getMessage());
            TestOut.println(e.getCallString());
            TestOut.println(e.getCallPointerString());

            assertEquals("Insufficient number of commands. Next command is one of: [ A ] or no command.", e.getMessage());
            assertEquals("test -h", e.getCallString());
            assertEquals("     ^", e.getCallPointerString());
        }
    }

    @Test
    public void optionInsteadOfCommand_neg() {

        Cli cli = createCli();
        String[] args = {"A", "-h"};

        try {
            cli.parse(args);
        } catch (UnrecognizedArgumentException e) {

            assertTrue(e instanceof InsufficientNrOfCommandsException);

            TestOut.println(e.getMessage());
            TestOut.println(e.getCallString());
            TestOut.println(e.getCallPointerString());

            assertEquals("Insufficient number of commands. Next command is one of: [ B ].", e.getMessage());
            assertEquals("test A -h", e.getCallString());
            assertEquals("       ^", e.getCallPointerString());
        }
    }

    @Test
    public void optionInsteadOfCommandWithFollowingOption_neg() {

        Cli cli = createCli();
        String[] args = {"A", "-h", "B"};

        try {
            cli.parse(args);
        } catch (UnrecognizedArgumentException e) {

            assertTrue(e instanceof InsufficientNrOfCommandsException);

            TestOut.println(e.getMessage());
            TestOut.println(e.getCallString());
            TestOut.println(e.getCallPointerString());

            assertEquals("Insufficient number of commands. Next command is one of: [ B ].", e.getMessage());
            assertEquals("test A -h B", e.getCallString());
            assertEquals("       ^", e.getCallPointerString());
        }
    }

    @Test
    public void CommandMissing_neg() {

        Cli cli = createCli();
        String[] args = {"A"};

        try {
            cli.parse(args);
        } catch (UnrecognizedArgumentException e) {

            assertTrue(e instanceof InsufficientNrOfCommandsException);

            TestOut.println(e.getMessage());
            TestOut.println(e.getCallString());
            TestOut.println(e.getCallPointerString());

            assertEquals("Insufficient number of commands. Next command is one of: [ B ].", e.getMessage());
            assertEquals("test A", e.getCallString());
            assertEquals("       ^", e.getCallPointerString());
        }
    }

}
