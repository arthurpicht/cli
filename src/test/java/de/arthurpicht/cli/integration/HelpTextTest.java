package de.arthurpicht.cli.integration;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.DefaultCommandBuilder;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.*;
import de.arthurpicht.cli.parameter.ParametersOne;
import de.arthurpicht.cli.parameter.ParametersVar;
import org.junit.jupiter.api.Test;

public class HelpTextTest {

    private CommandLineInterface getCommandLineInterface() {

        Options globalOptions = new Options()
                .add(new VersionOption())
                .add(new HelpOption())
                .add(new OptionBuilder().withLongName("stacktrace").withDescription("Show stacktrace on error occurence.").build("STACKTRACE"))
                .add(new OptionBuilder().withLongName("loglevel").withArgumentName("loglevel").withDescription("Log level.").build("LOGLEVEL"));

        DefaultCommand defaultCommand = new DefaultCommandBuilder()
                .withParameters(new ParametersOne())
                .withDescription("The default command")
                .build();

        Commands commands = new Commands();
        commands.setDefaultCommand(defaultCommand);
        commands.add(
                new CommandSequenceBuilder()
                        .addCommands("COMMAND_A")
                        .withSpecificOptions(
                                new Options()
                                        .add(new HelpOption())
                                        .add(new Option("A", 'A', "almost-all", false, "", "do not list implied . and .."))
                        )
                        .withParameters(new ParametersVar(1, "file", "Files to be processed."))
                        .withDescription("This is a description for command_A.")
                        .build()
        );
        commands.add(
                new CommandSequenceBuilder()
                        .addCommands("COMMAND_B")
                        .withSpecificOptions(
                                new Options()
                                .add(new HelpOption())
                                .add(new Option("B", 'b', "brrr", false, "", "The brrr option"))
                                .add(new Option("C", 'c', "cool", true, "what", "The cool option."))
                        )
                        .withParameters(new ParametersOne())
                        .build()
        );

        CommandLineInterfaceDescription commandLineInterfaceDescription
                = new CommandLineInterfaceDescriptionBuilder("test")
                .withDescription("A description for test.")
                .withVersion("v1.0.0")
                .withDate("18.02.2021")
                .build();

        return new CommandLineInterfaceBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .build(commandLineInterfaceDescription);
    }

    @Test
    public void specificHelp() throws CommandExecutorException, UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();
        String[] args = {"COMMAND_A", "-h"};
        commandLineInterface.execute(args);

    }

    @Test
    public void globalHelp() throws CommandExecutorException, UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();
        String[] args = {"-h"};
        commandLineInterface.execute(args);

    }

}
