package de.arthurpicht.cli.command.integration;

import de.arthurpicht.cli.*;
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

public class DefaultCommandTestWithGlobalOptionOnly {

    private static class DefaultCommandExecutor implements CommandExecutor {
        @Override
        public void execute(OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific, ParameterParserResult parameterParserResult) {
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
                .build("test");
    }

    @Test
    public void noArg() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = createCommandLineInterface();
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

        CommandLineInterface commandLineInterface = createCommandLineInterface();
        String[] args = {"--version"};
        CommandLineInterfaceCall commandLineInterfaceCall = commandLineInterface.parse(args);
        CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

        assertTrue(commandLineInterfaceResult.getOptionParserResultGlobal().hasOption("v"));
        assertTrue(commandLineInterfaceCall.getCommandExecutor() instanceof DefaultCommandExecutor);
        assertTrue(commandLineInterfaceCall.getCommandList().isEmpty());
        assertTrue(commandLineInterfaceResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(commandLineInterfaceResult.getParameterParserResult().getParameterList().isEmpty());
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
