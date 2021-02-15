package de.arthurpicht.cli.integration;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.help.HelpFormatter;
import de.arthurpicht.cli.option.Option;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.cli.parameter.ParametersVar;

public class HelpTextTest {

    private static class HelpExecutor implements CommandExecutor {

        private CommandLineInterfaceDefinition commandLineInterfaceDefinition;
        private Options specificOptions;
        private Parameters parameters;

        public HelpExecutor(CommandLineInterfaceDefinition commandLineInterfaceDefinition, Options specificOptions, Parameters parameters) {
            this.commandLineInterfaceDefinition = commandLineInterfaceDefinition;
            this.specificOptions = specificOptions;
            this.parameters = parameters;
        }

        @Override
        public void execute(CommandLineInterfaceCall commandLineInterfaceCall) {
            HelpFormatter.out(commandLineInterfaceCall);
        }

    }


    private CommandLineInterface getCommandLineInterface() {
        Commands commands = new Commands();
        commands.add(
                new CommandSequenceBuilder()
                        .addCommands("execute")
                        .withSpecificOptions(
                                new Options()
                                        .add(new OptionBuilder().withShortName('h').withLongName("help").withDescription("Show this help message and exit.").build("help"))
                                        .add(new Option("A", 'A', "almost-all", false, "", "do not list implied . and .."))
                        )
                        .withParameters(new ParametersVar(1))
                        .withCommandExecutor(new HelpTextTest.HelpExecutor(null, null, null))
                        // TODO null
                        .build()
        );
        commands.add(
                new CommandSequenceBuilder()
                        .addCommands("delete")
                        .withParameters(new ParametersVar(1))
                        .withCommandExecutor((commandLineInterfaceCall) -> {
                            System.out.println("Deleting the following items:");
                            for (String item : commandLineInterfaceCall.getParameterParserResult().getParameterList()) {
                                System.out.println(item);
                            }
                        })
                        .build()
        );

        return new CommandLineInterfaceBuilder().withCommands(commands).build("test");
    }

}
