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

public class DefaultCommandTestWithGlobalOptionOnly {

    private static class DefaultCommandExecutor implements CommandExecutor {
        @Override
        public void execute(OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific, List<String> parameterList) {
            // din
        }
    }

    private CommandLineInterface createCommandLineInterface() {

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
        return new CommandLineInterfaceBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .build();
    }

    @Test
    public void noArg() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = createCommandLineInterface();
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

        CommandLineInterface commandLineInterface = createCommandLineInterface();
        String[] args = {"--version"};
        ParserResult parserResult = commandLineInterface.parse(args);

        assertTrue(parserResult.getOptionParserResultGlobal().hasOption("v"));
        assertTrue(parserResult.getCommandExecutor() instanceof DefaultCommandExecutor);
        assertTrue(parserResult.getCommandList().isEmpty());
        assertTrue(parserResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(parserResult.getParameterList().isEmpty());
    }

    @Test
    public void commandOnly_neg() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = createCommandLineInterface();
        String[] args = {"A"};

        try {
            commandLineInterface.parse(args);
            fail(UnrecognizedArgumentException.class.getSimpleName() + " expected.");
        } catch (UnrecognizedArgumentException e) {
            // din
        }
    }

    @Test
    public void globalOptionAndCommand_neg() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = createCommandLineInterface();
        String[] args = {"-v", "A"};

        try {
            commandLineInterface.parse(args);
            fail(UnrecognizedArgumentException.class.getSimpleName() + " expected.");
        } catch (UnrecognizedArgumentException e) {
            // din
        }
    }

}
