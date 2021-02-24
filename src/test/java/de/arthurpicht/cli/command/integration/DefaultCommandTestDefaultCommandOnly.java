package de.arthurpicht.cli.command.integration;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.DefaultCommandBuilder;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultCommandTestDefaultCommandOnly {

    private static class DefaultCommandExecutor implements CommandExecutor {
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
                .setDefaultCommand(defaultCommand);
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
            fail(UnrecognizedArgumentException.class.getSimpleName() + " expected.");
        } catch (UnrecognizedArgumentException e) {
            assertEquals("Unrecognized argument: --version", e.getMessage());
        }
    }

    @Test
    public void commandOnly_neg() throws UnrecognizedArgumentException {

        Cli cli = createCliWithGlobalOptionAndCommand();
        String[] args = {"A"};

        try {
            cli.parse(args);
            fail(UnrecognizedArgumentException.class.getSimpleName() + " expected.");
        } catch (UnrecognizedArgumentException e) {
            assertEquals("Unrecognized argument: A", e.getMessage());
        }
    }

    @Test
    public void globalOptionAndCommand_neg() throws UnrecognizedArgumentException {

        Cli cli = createCliWithGlobalOptionAndCommand();
        String[] args = {"-v", "A"};

        try {
            cli.parse(args);
            fail(UnrecognizedArgumentException.class.getSimpleName() + " expected.");
        } catch (UnrecognizedArgumentException e) {
            assertEquals("Unrecognized argument: -v", e.getMessage());
        }
    }

}