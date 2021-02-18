package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CommandLineInterfaceCall;
import de.arthurpicht.cli.CommandLineInterfaceDefinition;
import de.arthurpicht.cli.CommandLineInterfaceDescription;
import de.arthurpicht.cli.CommandLineInterfaceResult;
import de.arthurpicht.cli.command.CommandParserResult;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.List;

public class HelpFormatter {

    public static final int COL_WIDTH = 30;

    public static void out(CommandLineInterfaceCall commandLineInterfaceCall) {
        printUsage(commandLineInterfaceCall);
        printDescription(commandLineInterfaceCall.getCommandLineInterfaceDefinition());
        printGlobalOptionsHelpString(commandLineInterfaceCall.getCommandLineInterfaceDefinition());
        printSpecificOptionsHelpString(commandLineInterfaceCall.getCommandLineInterfaceResult().getCommandParserResult());
        printParametersHelpString(commandLineInterfaceCall.getCommandLineInterfaceResult().getCommandParserResult());
//        printVersionAndDate(commandLineInterfaceCall.getCommandLineInterfaceDefinition());
    }

    private static void printUsage(CommandLineInterfaceCall call) {

        CommandLineInterfaceDefinition definition = call.getCommandLineInterfaceDefinition();
        CommandParserResult commandParserResult = call.getCommandLineInterfaceResult().getCommandParserResult();

        String usage = "Usage: " + call.getCommandLineInterfaceDefinition().getCommandLineInterfaceDescription().getExecutableName();

        if (definition.hasGlobalOptions()) {
            usage += " [global options]";
        }

        usage += " " + Strings.listing(call.getCommandList(), " ");

        if (commandParserResult.hasSpecificOptions()) {
            usage += " [specific options]";
        }

        if (commandParserResult.hasParameters()) {
            usage += " " + commandParserResult.getParameters().getHelpUsageSubString();
        }

        System.out.println(usage);
    }

    private static void printDescription(CommandLineInterfaceDefinition commandLineInterfaceDefinition) {
        System.out.println(commandLineInterfaceDefinition.getCommandLineInterfaceDescription().getDescription());
    }

    private static void printVersionAndDate(CommandLineInterfaceDefinition commandLineInterfaceDefinition) {
        String versionAndDate = getVersionAndDateString(commandLineInterfaceDefinition);
        if (Strings.isSpecified(versionAndDate))
            System.out.println(versionAndDate);
    }

    public static String getVersionAndDateString(CommandLineInterfaceDefinition commandLineInterfaceDefinition) {
        CommandLineInterfaceDescription commandLineInterfaceDescription
                = commandLineInterfaceDefinition.getCommandLineInterfaceDescription();

        String versionAndDate = "";

        if (commandLineInterfaceDescription.hasVersion()) {
            versionAndDate += "Version: " + commandLineInterfaceDescription.getVersion();
        }

        if (commandLineInterfaceDescription.hasDate()) {
            if (Strings.isSpecified(versionAndDate)) versionAndDate += " ";
            versionAndDate += "from: " + commandLineInterfaceDescription.getDate();
        }

        return versionAndDate;
    }

    private static void printGlobalOptionsHelpString(CommandLineInterfaceDefinition commandLineInterfaceDefinition) {

        if (!commandLineInterfaceDefinition.hasGlobalOptions()) return;

        System.out.println("Global options:");

        Options globalOptions = commandLineInterfaceDefinition.getGlobalOptions();
        System.out.println(globalOptions.getHelpString());
    }

    private static void printSpecificOptionsHelpString(CommandParserResult commandParserResult) {

        if (!commandParserResult.hasSpecificOptions()) return;

        System.out.println("Specific options:");

        Options specificOptions = commandParserResult.getSpecificOptions();
        System.out.println(specificOptions.getHelpString());
    }

    private static void printParametersHelpString(CommandParserResult commandParserResult) {

        if (!commandParserResult.hasSpecificOptions()) return;

        System.out.println("Parameters:");

        Parameters parameters = commandParserResult.getParameters();
        System.out.println(parameters.getHelpString());
    }

}
