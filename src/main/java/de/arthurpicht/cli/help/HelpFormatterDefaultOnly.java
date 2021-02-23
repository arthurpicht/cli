package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CommandLineInterfaceCall;
import de.arthurpicht.cli.CommandLineInterfaceDefinition;
import de.arthurpicht.cli.CommandLineInterfaceDescription;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;

import static de.arthurpicht.cli.help.HelpFormatterCommons.INDENT;

public class HelpFormatterDefaultOnly {

    public void out(CommandLineInterfaceCall commandLineInterfaceCall) {

        CommandLineInterfaceDefinition commandLineInterfaceDefinition = commandLineInterfaceCall.getCommandLineInterfaceDefinition();
        CommandLineInterfaceDescription commandLineInterfaceDescription = commandLineInterfaceDefinition.getCommandLineInterfaceDescription();
        DefaultCommand defaultCommand = commandLineInterfaceDefinition.getDefaultCommand();

        HelpFormatterCommons.printHeaderString(commandLineInterfaceDefinition);

        HelpFormatterCommons.printExecutableDescription(commandLineInterfaceDescription);

        System.out.println("Usage:");
        System.out.println(INDENT + HelpFormatterCommons.getUsageOfDefaultCommand(commandLineInterfaceDefinition, true));

        if (defaultCommand.hasDescription()) {
            System.out.println(HelpFormatterCommons.indentString(defaultCommand.getDescription()));
        }

        if (commandLineInterfaceDefinition.hasGlobalOptions()) {
            System.out.println("Options:");
            Options globalOptions = commandLineInterfaceDefinition.getGlobalOptions();
            System.out.println(HelpFormatterCommons.indentString(globalOptions.getHelpString()));
        }

        if (defaultCommand.hasParameters()) {
            System.out.println("Parameters:");
            Parameters parameters = defaultCommand.getParameters();
            System.out.println(HelpFormatterCommons.indentString(parameters.getHelpString()));
        }
    }

}
