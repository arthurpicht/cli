package de.arthurpicht.cli.command.integration;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.DefaultCommandBuilder;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultCommandTestWithGlobalOptionOnly {

    private static class DefaultCommandExecutor implements CommandExecutor {
        @Override
        public void execute(CliCall cliCall) {
            // din
        }
    }

    private Cli createCli() {

        Options globalOptions = new Options()
                .add(new OptionBuilder()
                        .withShortName('v')
                        .withLongName("version")
                        .build("v"));
        DefaultCommand defaultCommand = new DefaultCommandBuilder()
                .withCommandExecutor(new DefaultCommandExecutor())
                .build();
        Commands commands = new Commands()
                .setDefaultCommand(defaultCommand);
        return new CliBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .build("test");
    }

    @Test
    public void noArg() throws UnrecognizedArgumentException {

        Cli cli = createCli();
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

        Cli cli = createCli();
        String[] args = {"--version"};
        CliCall cliCall = cli.parse(args);
        CliResult cliResult = cliCall.getCliResult();

        assertTrue(cliResult.getOptionParserResultGlobal().hasOption("v"));
        assertTrue(cliCall.getCommandExecutor() instanceof DefaultCommandExecutor);
        assertTrue(cliCall.getCommandList().isEmpty());
        assertTrue(cliResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(cliResult.getParameterParserResult().getParameterList().isEmpty());
    }

    @Test
    public void commandOnly_neg() throws UnrecognizedArgumentException {

        Cli cli = createCli();
        String[] args = {"A"};

        try {
            cli.parse(args);
            fail(UnrecognizedArgumentException.class.getSimpleName() + " expected.");
        } catch (UnrecognizedArgumentException e) {
            // din
        }
    }

    @Test
    public void globalOptionAndCommand_neg() throws UnrecognizedArgumentException {

        Cli cli = createCli();
        String[] args = {"-v", "A"};

        try {
            cli.parse(args);
            fail(UnrecognizedArgumentException.class.getSimpleName() + " expected.");
        } catch (UnrecognizedArgumentException e) {
            // din
        }
    }

}
