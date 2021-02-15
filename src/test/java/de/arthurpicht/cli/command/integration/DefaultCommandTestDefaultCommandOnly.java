package de.arthurpicht.cli.command.integration;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.DefaultCommandBuilder;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.parameter.ParameterParserResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultCommandTestDefaultCommandOnly {

    private static class DefaultCommandExecutor implements CommandExecutor {
        @Override
        public void execute(CommandLineInterfaceCall commandLineInterfaceCall) {
            // din
        }
    }

    private CommandLineInterface createCommandLineInterfaceWithGlobalOptionAndCommand() {

        DefaultCommand defaultCommand = new DefaultCommandBuilder()
                .withCommandExecutor(new DefaultCommandExecutor())
                .build();
        Commands commands = new Commands()
                .setDefaultCommand(defaultCommand);
        return new CommandLineInterfaceBuilder()
                .withCommands(commands)
                .build("test");
    }

    @Test
    public void noArg() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = createCommandLineInterfaceWithGlobalOptionAndCommand();
        String[] args = {};
        CommandLineInterfaceCall commandLineInterfaceCall = commandLineInterface.parse(args);
        CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

        assertFalse(commandLineInterfaceResult.getOptionParserResultGlobal().hasOption("v"));
        assertTrue(commandLineInterfaceCall.getCommandExecutor() instanceof DefaultCommandExecutor);
        assertTrue(commandLineInterfaceCall.getCommandList().isEmpty());
        assertTrue(commandLineInterfaceResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(commandLineInterfaceResult.getParameterParserResult().isEmpty());
    }

    @Test
    public void globalOptionOnly_neg() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = createCommandLineInterfaceWithGlobalOptionAndCommand();
        String[] args = {"--version"};

        try {
            commandLineInterface.parse(args);
            fail(UnrecognizedArgumentException.class.getSimpleName() + " expected.");
        } catch (UnrecognizedArgumentException e) {
            assertEquals("Unrecognized argument: --version", e.getMessage());
        }
    }

    @Test
    public void commandOnly_neg() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = createCommandLineInterfaceWithGlobalOptionAndCommand();
        String[] args = {"A"};

        try {
            commandLineInterface.parse(args);
            fail(UnrecognizedArgumentException.class.getSimpleName() + " expected.");
        } catch (UnrecognizedArgumentException e) {
            assertEquals("Unrecognized argument: A", e.getMessage());
        }
    }

    @Test
    public void globalOptionAndCommand_neg() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = createCommandLineInterfaceWithGlobalOptionAndCommand();
        String[] args = {"-v", "A"};

        try {
            commandLineInterface.parse(args);
            fail(UnrecognizedArgumentException.class.getSimpleName() + " expected.");
        } catch (UnrecognizedArgumentException e) {
            assertEquals("Unrecognized argument: -v", e.getMessage());
        }
    }

}