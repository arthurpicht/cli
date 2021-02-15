package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CommandLineInterfaceCall;
import de.arthurpicht.cli.CommandLineInterfaceDefinition;
import de.arthurpicht.cli.CommandLineInterfaceResult;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.List;

public class HelpFormatter {

    public static void test() {

    }

    public static void out(CommandLineInterfaceCall commandLineInterfaceCall) {
        System.out.println("Usage: " + getUsage(commandLineInterfaceCall));
        System.out.println("Explanation of command ...");
        printGlobalOptionsHelpString(commandLineInterfaceCall.getCommandLineInterfaceDefinition());
//        printSpecificOptionsHelpString(commandLineInterfaceCall.getOptionParserResultSpecific());

    }

    private static String getUsage(CommandLineInterfaceCall call) {

        CommandLineInterfaceResult result = call.getCommandLineInterfaceResult();

        String usage = "usage: " + call.getCommandLineInterfaceDefinition().getExecutableName();

        if (result.hasGlobalOptions()) {
            usage += " [global options]";
        }

        usage += " " + Strings.listing(call.getCommandList(), " ");

        if (result.hasSpecificOptions()) {
            usage += " [specific options]";
        }

        if (result.hasParameters()) {
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
