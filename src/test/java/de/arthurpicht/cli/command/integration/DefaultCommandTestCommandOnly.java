package de.arthurpicht.cli.command.integration;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.DefaultCommandBuilder;
import de.arthurpicht.cli.command.exceptions.InsufficientNrOfCommandsException;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultCommandTestCommandOnly {

    private static class DefaultCommandExecutor implements CommandExecutor {
        @Override
        public void execute(CliCall cliCall) {
            // din
        }
    }

    private static class CommandExecutorA implements CommandExecutor {
        @Override
        public void execute(CliCall cliCall) {
            // din
        }
    }

    private Cli createCliWithGlobalOptionAndCommand() {

        DefaultCommand defaultCommand = new DefaultCommandBuilder()
                .withCommandExecutor(new DefaultCommandExecutor())
                .build();
        Commands commands = new Commands()
                .setDefaultCommand(defaultCommand)
                .add(new CommandSequenceBuilder().addCommands("A").withCommandExecutor(new CommandExecutorA()).build());
        return new CliBuilder()
                .withCommands(commands)
                .build("test");
    }

    @Test
    public void noArg() throws UnrecognizedArgumentException {

        Cli cli = createCliWithGlobalOptionAndCommand();
        String[] args = {};
        CliCall cliCall = cli.parse(args);
        CliResult cliResult = cliCall.getCliResult();

        assertFalse(cliResult.getOptionParserResultGlobal().hasOption("v"));
        assertTrue(cliCall.getCommandExecutor() instanceof DefaultCommandExecutor);
        assertTrue(cliCall.getCommandList().isEmpty());
        assertTrue(cliResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(cliResult.getParameterParserResult().isEmpty());
    }

    @Test
    public void globalOptionOnly_neg() throws UnrecognizedArgumentException {

        Cli cli = createCliWithGlobalOptionAndCommand();
        String[] args = {"--version"};

        try {
            cli.parse(args);
            fail(InsufficientNrOfCommandsException.class.getSimpleName() + " expected.");
        } catch (InsufficientNrOfCommandsException e) {
            assertEquals("Insufficient number of commands. Next command is one of: [ A ] or no command.", e.getMessage());
        }
    }

    @Test
    public void commandOnly() throws UnrecognizedArgumentException {

        Cli cli = createCliWithGlobalOptionAndCommand();
        String[] args = {"A"};
        CliCall cliCall = cli.parse(args);
        CliResult cliResult = cliCall.getCliResult();

        assertTrue(cliResult.getOptionParserResultGlobal().isEmpty());
        assertTrue(cliCall.getCommandExecutor() instanceof CommandExecutorA);
        assertEquals("A", cliCall.getCommandList().get(0));
        assertTrue(cliResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(cliResult.getParameterParserResult().isEmpty());
    }

    @Test
    public void globalOptionAndCommand_neg() throws UnrecognizedArgumentException {

        Cli cli = createCliWithGlobalOptionAndCommand();
        String[] args = {"-v", "A"};
        try {
            cli.parse(args);
            fail(InsufficientNrOfCommandsException.class.getSimpleName() + " expected.");
        } catch (InsufficientNrOfCommandsException e) {
            assertEquals("Insufficient number of commands. Next command is one of: [ A ] or no command.", e.getMessage());
        }
    }

}