package de.arthurpicht.cli.command.integration;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.DefaultCommandBuilder;
import de.arthurpicht.cli.command.exceptions.InsufficientNrOfCommandsException;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.parameter.ParameterParserResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultCommandTestCommandOnly {

    private static class DefaultCommandExecutor implements CommandExecutor {
        @Override
        public void execute(CommandLineInterfaceCall commandLineInterfaceCall) {
            // din
        }
    }

    private static class CommandExecutorA implements CommandExecutor {
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
                .setDefaultCommand(defaultCommand)
                .add(new CommandSequenceBuilder().addCommands("A").withCommandExecutor(new CommandExecutorA()).build());
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
            fail(InsufficientNrOfCommandsException.class.getSimpleName() + " expected.");
        } catch (InsufficientNrOfCommandsException e) {
            assertEquals("Insufficient number of commands. Next command is one of: [ A ] or no command.", e.getMessage());
        }
    }

    @Test
    public void commandOnly() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = createCommandLineInterfaceWithGlobalOptionAndCommand();
        String[] args = {"A"};
        CommandLineInterfaceCall commandLineInterfaceCall = commandLineInterface.parse(args);
        CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

        assertTrue(commandLineInterfaceResult.getOptionParserResultGlobal().isEmpty());
        assertTrue(commandLineInterfaceCall.getCommandExecutor() instanceof CommandExecutorA);
        assertEquals("A", commandLineInterfaceCall.getCommandList().get(0));
        assertTrue(commandLineInterfaceResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(commandLineInterfaceResult.getParameterParserResult().isEmpty());
    }

    @Test
    public void globalOptionAndCommand_neg() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = createCommandLineInterfaceWithGlobalOptionAndCommand();
        String[] args = {"-v", "A"};
        try {
            commandLineInterface.parse(args);
            fail(InsufficientNrOfCommandsException.class.getSimpleName() + " expected.");
        } catch (InsufficientNrOfCommandsException e) {
            assertEquals("Insufficient number of commands. Next command is one of: [ A ] or no command.", e.getMessage());
        }
    }

}