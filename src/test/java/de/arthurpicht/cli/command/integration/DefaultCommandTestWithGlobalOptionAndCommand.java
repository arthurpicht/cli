package de.arthurpicht.cli.command.integration;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.CommandLineInterfaceBuilder;
import de.arthurpicht.cli.ParserResult;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.DefaultCommandBuilder;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.option.Options;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultCommandTestWithGlobalOptionAndCommand {

    private static class DefaultCommandExecutor implements CommandExecutor {
        @Override
        public void execute(OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific, List<String> parameterList) {
            // din
        }
    }

    private static class CommandExecutorA implements CommandExecutor {
        @Override
        public void execute(OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific, List<String> parameterList) {
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
    public void globalOptionOnly() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = createCommandLineInterfaceWithGlobalOptionAndCommand();
        String[] args = {"--version"};
        ParserResult parserResult = commandLineInterface.parse(args);

        assertTrue(parserResult.getOptionParserResultGlobal().hasOption("v"));
        assertTrue(parserResult.getCommandExecutor() instanceof DefaultCommandExecutor);
        assertTrue(parserResult.getCommandList().isEmpty());
        assertTrue(parserResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(parserResult.getParameterList().isEmpty());
    }

    @Test
    public void commandOnly() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = createCommandLineInterfaceWithGlobalOptionAndCommand();
        String[] args = {"A"};
        ParserResult parserResult = commandLineInterface.parse(args);

        assertTrue(parserResult.getOptionParserResultGlobal().isEmpty());
        assertTrue(parserResult.getCommandExecutor() instanceof CommandExecutorA);
        assertEquals("A", parserResult.getCommandList().get(0));
        assertTrue(parserResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(parserResult.getParameterList().isEmpty());
    }

    @Test
    public void globalOptionAndCommand() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = createCommandLineInterfaceWithGlobalOptionAndCommand();
        String[] args = {"-v", "A"};
        ParserResult parserResult = commandLineInterface.parse(args);

        assertTrue(parserResult.getOptionParserResultGlobal().hasOption("v"));
        assertTrue(parserResult.getCommandExecutor() instanceof CommandExecutorA);
        assertEquals("A", parserResult.getCommandList().get(0));
        assertTrue(parserResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(parserResult.getParameterList().isEmpty());
    }

}
