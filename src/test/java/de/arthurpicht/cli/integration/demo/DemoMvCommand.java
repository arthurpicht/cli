package de.arthurpicht.cli.integration.demo;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.DefaultCommandBuilder;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.HelpOption;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.ParametersNBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DemoMvCommand {

    private CommandLineInterface createCommandLineInterface() {

        Options globalOptions = new Options().add(new HelpOption());

        DefaultCommand defaultCommand =
                new DefaultCommandBuilder()
                        .withParameters(
                                new ParametersNBuilder()
                                        .addParameter("source", "file to be moved")
                                        .addParameter("destination", "destination file to be moved to")
                                        .build())
                        .withCommandExecutor(
                                (commandLineInterfaceCall)
                                        -> System.out.println(
                                        "Copy file from " + commandLineInterfaceCall.getParameterParserResult().getParameterList().get(0) + " to "
                                                + commandLineInterfaceCall.getParameterParserResult().getParameterList().get(1))
                        ).build();

        Commands commands = new Commands().setDefaultCommand(defaultCommand);

        CommandLineInterfaceDescription commandLineInterfaceDescription =
                new CommandLineInterfaceDescriptionBuilder("mv")
                .withDescription("(Demo) Move file from source to destination.")
                .withDate("22.02.2021")
                .withVersion("1.0")
                .build();

        return new CommandLineInterfaceBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .build(commandLineInterfaceDescription);
    }

    @Test
    public void regularCall() throws UnrecognizedArgumentException, CommandExecutorException {

        CommandLineInterface commandLineInterface = createCommandLineInterface();

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

    @Test
    public void help() throws CommandExecutorException, UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = createCommandLineInterface();
        String[] args = {"-h"};

        CommandLineInterfaceCall call = commandLineInterface.execute(args);

        // TODO assert out

    }

}
