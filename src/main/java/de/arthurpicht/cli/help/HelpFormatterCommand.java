package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CliDefinition;
import de.arthurpicht.cli.command.CommandParserResult;
import de.arthurpicht.cli.common.CLIContext;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.utils.core.strings.Strings;

public class HelpFormatterCommand {

    public void out(CliCall cliCall) {

        CliDefinition cliDefinition = cliCall.getCliDefinition();
        CommandParserResult commandParserResult = cliCall.getCliResult().getCommandParserResult();

        printUsageString(cliCall);

        printDescription(commandParserResult);

        printGlobalOptions(cliDefinition);

        printSpecificOptions(commandParserResult);

        printParameters(commandParserResult);
    }

    private void printUsageString(CliCall call) {

        CliDefinition definition = call.getCliDefinition();
        CommandParserResult commandParserResult = call.getCliResult().getCommandParserResult();

        String usage = "Usage: " + call.getCliDefinition().getCliDescription().getExecutableName();

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

    private void printGlobalOptions(CliDefinition cliDefinition) {
        if (cliDefinition.hasGlobalOptions()) {
            CLIContext.out.println("Global Options:");
            Options globalOptions = cliDefinition.getGlobalOptions();
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
