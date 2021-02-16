package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CommandLineInterfaceCall;
import de.arthurpicht.cli.CommandLineInterfaceDefinition;
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
        System.out.println(getUsage(commandLineInterfaceCall));
        System.out.println("Explanation of command ...");
        printGlobalOptionsHelpString(commandLineInterfaceCall.getCommandLineInterfaceDefinition());
        printSpecificOptionsHelpString(commandLineInterfaceCall.getCommandLineInterfaceResult().getCommandParserResult());
        printParametersHelpString(commandLineInterfaceCall.getCommandLineInterfaceResult().getCommandParserResult());
    }

    private static String getUsage(CommandLineInterfaceCall call) {

        CommandLineInterfaceDefinition definition = call.getCommandLineInterfaceDefinition();
        CommandParserResult commandParserResult = call.getCommandLineInterfaceResult().getCommandParserResult();

        String usage = "Usage: " + call.getCommandLineInterfaceDefinition().getExecutableName();

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

        return usage;
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
