package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CommandLineInterfaceCall;
import de.arthurpicht.cli.CommandLineInterfaceDefinition;
import de.arthurpicht.cli.command.CommandParserResult;
import de.arthurpicht.cli.common.CLIContext;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.utils.core.strings.Strings;

import java.io.PrintStream;

public class HelpFormatterCommand {

    public void out(CommandLineInterfaceCall commandLineInterfaceCall) {

        CommandLineInterfaceDefinition commandLineInterfaceDefinition = commandLineInterfaceCall.getCommandLineInterfaceDefinition();
        CommandParserResult commandParserResult = commandLineInterfaceCall.getCommandLineInterfaceResult().getCommandParserResult();

        printUsageString(commandLineInterfaceCall);

        printDescription(commandParserResult);

        printGlobalOptions(commandLineInterfaceDefinition);

        printSpecificOptions(commandParserResult);

        printParameters(commandParserResult);
    }

    private void printUsageString(CommandLineInterfaceCall call) {

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

        CLIContext.out.println(usage);
    }


    private void printDescription(CommandParserResult commandParserResult) {
        if (commandParserResult.hasDescription()) {
            String description = HelpFormatterCommons.indentString(commandParserResult.getDescription());
            CLIContext.out.println(description);
        }
    }

    private void printGlobalOptions(CommandLineInterfaceDefinition commandLineInterfaceDefinition) {
        if (commandLineInterfaceDefinition.hasGlobalOptions()) {
            CLIContext.out.println("Global Options:");
            Options globalOptions = commandLineInterfaceDefinition.getGlobalOptions();
            CLIContext.out.println(HelpFormatterCommons.indentString(globalOptions.getHelpString()));
        }
    }

    private void printSpecificOptions(CommandParserResult commandParserResult) {
        if (commandParserResult.hasSpecificOptions()) {
            CLIContext.out.println("Specific options:");
            Options specificOptions = commandParserResult.getSpecificOptions();
            CLIContext.out.println(HelpFormatterCommons.indentString(specificOptions.getHelpString()));
        }
    }

    private void printParameters(CommandParserResult commandParserResult) {
        if (commandParserResult.hasParameters()) {
            CLIContext.out.println("Parameters:");
            Parameters parameters = commandParserResult.getParameters();
            CLIContext.out.println(HelpFormatterCommons.indentString(parameters.getHelpString()));
        }
    }

}
