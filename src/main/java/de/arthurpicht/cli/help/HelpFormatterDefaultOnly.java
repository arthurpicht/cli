package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CommandLineInterfaceCall;
import de.arthurpicht.cli.CommandLineInterfaceDefinition;
import de.arthurpicht.cli.CommandLineInterfaceDescription;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.parameter.Parameters;

import static de.arthurpicht.cli.help.HelpFormatter.INDENT;

public class HelpFormatterDefaultOnly {

    public void out(CommandLineInterfaceCall commandLineInterfaceCall) {

        CommandLineInterfaceDefinition commandLineInterfaceDefinition = commandLineInterfaceCall.getCommandLineInterfaceDefinition();
        CommandLineInterfaceDescription commandLineInterfaceDescription = commandLineInterfaceDefinition.getCommandLineInterfaceDescription();
        DefaultCommand defaultCommand = commandLineInterfaceDefinition.getDefaultCommand();


        System.out.println(HelpFormatter.getHeaderString(commandLineInterfaceDefinition));

        if (commandLineInterfaceDescription.hasDescription()) {
            String description = commandLineInterfaceDescription.getDescription();
            System.out.println(HelpFormatter.indentString(description));
        }

        System.out.println("Usage:");

        System.out.println(INDENT + HelpFormatter.getUsageOfDefaultCommand(commandLineInterfaceDefinition));

        if (defaultCommand.hasDescription()) {
            System.out.println(HelpFormatter.indentString(defaultCommand.getDescription()));
        }

        HelpFormatter.printGlobalOptionsHelpString(commandLineInterfaceDefinition);

        if (defaultCommand.hasParameters()) {
            System.out.println("Parameters:");

            Parameters parameters = defaultCommand.getParameters();
            System.out.println(HelpFormatter.indentString(parameters.getHelpString()));

        }



        // commandParserResult.getParameters().getHelpUsageSubString();



    }
}
