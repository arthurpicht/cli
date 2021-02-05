package de.arthurpicht.cli.command.integration;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.CommandLineInterfaceBuilder;
import de.arthurpicht.cli.ParserResult;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.DefaultCommandBuilder;
import de.arthurpicht.cli.command.exceptions.InsufficientNrOfCommandsException;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionParserResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultCommandTestDefaultCommandOnly {

    private static class DefaultCommandExecutor implements CommandExecutor {
        @Override
        public void execute(OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific, List<String> parameterList) {
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
                .build();
    }

    @Test
    public void noArg() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = createCommandLineInterfaceWithGlobalOptionAndCommand();
        String[] args = {};
        ParserResult parserResult = commandLineInterface.parse(args);

        assertFalse(parserResult.getOptionParserResultGlobal().hasOption("v"));
        assertTrue(parserResult.getCommandExecutor() instanceof DefaultCommandExecutor);
        assertTrue(parserResult.getCommandList().isEmpty());
        assertTrue(parserResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(parserResult.getParameterList().isEmpty());
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