package de.arthurpicht.cli.integration.demo;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.DefaultCommandBuilder;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.parameter.ParametersVar;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DemoMvCommand {

    @Test
    public void mv() throws UnrecognizedArgumentException, CommandExecutorException {

        DefaultCommand defaultCommand =
                new DefaultCommandBuilder()
                        .withParameters(new ParametersVar(2))
                        .withCommandExecutor(
                                (optionParserResultGlobal, commandList, optionParserResultSpecific, parameterParserResult)
                                        -> System.out.println(
                                                "Copy file from " + parameterParserResult.getParameterList().get(0) + " to "
                                                        + parameterParserResult.getParameterList().get(1))
                        ).build();

        Commands commands = new Commands().setDefaultCommand(defaultCommand);
        CommandLineInterface commandLineInterface = new CommandLineInterfaceBuilder().withCommands(commands).build("test");

        String[] args = {"source", "destination"};

        CommandLineInterfaceCall call = commandLineInterface.execute(args);
        CommandLineInterfaceResult result = call.getCommandLineInterfaceResult();

        assertTrue(result.getOptionParserResultGlobal().isEmpty());
        assertTrue(result.getCommandParserResult().getCommandStringList().isEmpty());
        assertTrue(result.getOptionParserResultSpecific().isEmpty());
        assertEquals(2, result.getParameterParserResult().getNrOfParameters());
        assertTrue(result.getParameterParserResult().getParameterList().contains("source"));
        assertTrue(result.getParameterParserResult().getParameterList().contains("destination"));
    }

}
