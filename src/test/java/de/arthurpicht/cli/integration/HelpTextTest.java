package de.arthurpicht.cli.integration;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.help.HelpFormatter;
import de.arthurpicht.cli.option.HelpOption;
import de.arthurpicht.cli.option.Option;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.ParametersVar;
import org.junit.jupiter.api.Test;

public class HelpTextTest {

    private static class HelpExecutor implements CommandExecutor {

        @Override
        public void execute(CommandLineInterfaceCall commandLineInterfaceCall) {
            if (commandLineInterfaceCall.getCommandLineInterfaceResult().getOptionParserResultSpecific().hasOption("HELP")) {
                HelpFormatter.out(commandLineInterfaceCall);
            }
        }
    }

    private CommandLineInterface getCommandLineInterface() {

        Options globalOptions = new Options()
                .add(new OptionBuilder().withShortName('v').withLongName("version").withDescription("Show version and exit.").build("VERSION"))
                .add(new OptionBuilder().withLongName("stacktrace").withDescription("Show stacktrace on error occurence.").build("STACKTRACE"))
                .add(new OptionBuilder().withLongName("loglevel").withArgumentName("loglevel").withDescription("Log level.").build("LOGLEVEL"));

        Commands commands = new Commands();
        commands.add(
                new CommandSequenceBuilder()
                        .addCommands("COMMAND_A")
                        .withSpecificOptions(
                                new Options()
                                        .add(new HelpOption())
                                        .add(new Option("A", 'A', "almost-all", false, "", "do not list implied . and .."))
                        )
                        .withParameters(new ParametersVar(1, "file", "Files to be processed."))
                        .withCommandExecutor(new HelpTextTest.HelpExecutor())
                        .build()
        );
//        commands.add(
//                new CommandSequenceBuilder()
//                        .addCommands("delete")
//                        .withParameters(new ParametersVar(1))
//                        .withCommandExecutor((commandLineInterfaceCall) -> {
//                            System.out.println("Deleting the following items:");
//                            for (String item : commandLineInterfaceCall.getParameterParserResult().getParameterList()) {
//                                System.out.println(item);
//                            }
//                        })
//                        .build()
//        );

        return new CommandLineInterfaceBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .build("test");
    }

    @Test
    public void test() throws CommandExecutorException, UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();
        String[] args = {"COMMAND_A", "-h"};
        commandLineInterface.execute(args);

    }

}
