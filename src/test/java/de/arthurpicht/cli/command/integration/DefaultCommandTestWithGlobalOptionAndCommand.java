package de.arthurpicht.cli.command.integration;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.DefaultCommandBuilder;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultCommandTestWithGlobalOptionAndCommand {

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

        Options globalOptions = new Options()
                .add(new OptionBuilder()
                        .withShortName('v')
                        .withLongName("version")
                        .build("v"));
        DefaultCommand defaultCommand = new DefaultCommandBuilder()
                .withCommandExecutor(new DefaultCommandExecutor())
                .build();
        Commands commands = new Commands()
                .setDefaultCommand(defaultCommand)
                .add(new CommandSequenceBuilder().addCommands("A").withCommandExecutor(new CommandExecutorA()).build());
        return new CliBuilder()
                .withGlobalOptions(globalOptions)
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
    public void globalOptionOnly() throws UnrecognizedArgumentException {

        Cli cli = createCliWithGlobalOptionAndCommand();
        String[] args = {"--version"};
        CliCall cliCall = cli.parse(args);
        CliResult cliResult = cliCall.getCliResult();

        assertTrue(cliResult.getOptionParserResultGlobal().hasOption("v"));
        assertTrue(cliCall.getCommandExecutor() instanceof DefaultCommandExecutor);
        assertTrue(cliCall.getCommandList().isEmpty());
        assertTrue(cliResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(cliResult.getParameterParserResult().isEmpty());
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
    public void globalOptionAndCommand() throws UnrecognizedArgumentException {

        Cli cli = createCliWithGlobalOptionAndCommand();
        String[] args = {"-v", "A"};
        CliCall cliCall = cli.parse(args);
        CliResult cliResult = cliCall.getCliResult();

        assertTrue(cliResult.getOptionParserResultGlobal().hasOption("v"));
        assertTrue(cliCall.getCommandExecutor() instanceof CommandExecutorA);
        assertEquals("A", cliCall.getCommandList().get(0));
        assertTrue(cliResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(cliResult.getParameterParserResult().isEmpty());
    }

}
