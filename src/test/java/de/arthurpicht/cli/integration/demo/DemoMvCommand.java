package de.arthurpicht.cli.integration.demo;

import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.CommandLineInterfaceBuilder;
import de.arthurpicht.cli.ParserResult;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.DefaultCommandBuilder;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.parameter.ParametersVar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DemoMvCommand {

    @Test
    public void mv() throws UnrecognizedArgumentException {

        DefaultCommand defaultCommand =
                new DefaultCommandBuilder()
                        .withParameters(new ParametersVar(2))
                        .withCommandExecutor(
                                (optionParserResultGlobal, commandList, optionParserResultSpecific, parameterList)
                                        -> System.out.println(
                                                "Copy file from " + parameterList.get(0) + " to "
                                                        + parameterList.get(1))
                        ).build();

        Commands commands = new Commands().setDefaultCommand(defaultCommand);
        CommandLineInterface commandLineInterface = new CommandLineInterfaceBuilder().withCommands(commands).build();

        String[] args = {"source", "destination"};

        ParserResult parserResult = commandLineInterface.execute(args);

        assertTrue(parserResult.getOptionParserResultGlobal().isEmpty());
        assertTrue(parserResult.getCommandList().isEmpty());
        assertTrue(parserResult.getOptionParserResultSpecific().isEmpty());
        assertEquals(2, parserResult.getParameterList().size());
        assertTrue(parserResult.getParameterList().contains("source"));
        assertTrue(parserResult.getParameterList().contains("destination"));
    }

}
