package de.arthurpicht.cli.command.integration;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.DefaultCommandBuilder;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.ParameterParserResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultCommandTestWithGlobalOptionAndCommand {

    private static class DefaultCommandExecutor implements CommandExecutor {
        @Override
        public void execute(OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific, ParameterParserResult parameterParserResult) {
            // din
        }
    }

    private static class CommandExecutorA implements CommandExecutor {
        @Override
        public void execute(OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific, ParameterParserResult parameterParserResult) {
            // din
        }
    }

    private CommandLineInterface createCommandLineInterfaceWithGlobalOptionAndCommand() {

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
        return new CommandLineInterfaceBuilder()
                .withGlobalOptions(globalOptions)
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
    public void globalOptionOnly() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = createCommandLineInterfaceWithGlobalOptionAndCommand();
        String[] args = {"--version"};
        CommandLineInterfaceCall commandLineInterfaceCall = commandLineInterface.parse(args);
        CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

        assertTrue(commandLineInterfaceResult.getOptionParserResultGlobal().hasOption("v"));
        assertTrue(commandLineInterfaceCall.getCommandExecutor() instanceof DefaultCommandExecutor);
        assertTrue(commandLineInterfaceCall.getCommandList().isEmpty());
        assertTrue(commandLineInterfaceResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(commandLineInterfaceResult.getParameterParserResult().isEmpty());
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
    public void globalOptionAndCommand() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = createCommandLineInterfaceWithGlobalOptionAndCommand();
        String[] args = {"-v", "A"};
        CommandLineInterfaceCall commandLineInterfaceCall = commandLineInterface.parse(args);
        CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

        assertTrue(commandLineInterfaceResult.getOptionParserResultGlobal().hasOption("v"));
        assertTrue(commandLineInterfaceCall.getCommandExecutor() instanceof CommandExecutorA);
        assertEquals("A", commandLineInterfaceCall.getCommandList().get(0));
        assertTrue(commandLineInterfaceResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(commandLineInterfaceResult.getParameterParserResult().isEmpty());
    }

}
