package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CommandLineInterfaceDefinition;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.List;

public class HelpFormatter {

    public static void out(CommandLineInterfaceDefinition cliDefinition, Options specificOptions, List<String> commandList, Parameters parameters) {
        System.out.println("Usage: " + getUsage(cliDefinition, specificOptions, commandList, parameters));
        System.out.println("Explanation of command ...");
        printGlobalOptionsHelpString(cliDefinition);
        printSpecificOptionsHelpString(specificOptions);

    }

    private static String getUsage(CommandLineInterfaceDefinition cliDefinition, Options specificOptions, List<String> commandList, Parameters parameters) {

        String usage = "usage: " + cliDefinition.getExecutableName();

        if (cliDefinition.hasGlobalOptions()) {
            usage += " [global options]";
        }

        usage += " " + Strings.listing(commandList, " ");

        if (specificOptions != null) {
            usage += " [specific options]";
        }

        if (parameters != null) {
            usage += " <parametersTODO>";
        }

        return usage;
    }

    private static void printGlobalOptionsHelpString(CommandLineInterfaceDefinition commandLineInterfaceDefinition) {

        if (!commandLineInterfaceDefinition.hasGlobalOptions()) return;

        System.out.println("Global options:");

        Options globalOptions = commandLineInterfaceDefinition.getGlobalOptions();
        System.out.println(globalOptions.getHelpString());
    }

    private static void printSpecificOptionsHelpString(Options specificOptions) {

        if (specificOptions == null || specificOptions.isEmpty()) return;

        System.out.println("Specific options:");

        System.out.println(specificOptions.getHelpString());
    }

    private static void printParameters(Parameters parameters) {

        System.out.println("Parameters:");
        System.out.println("TODO");
    }

}
