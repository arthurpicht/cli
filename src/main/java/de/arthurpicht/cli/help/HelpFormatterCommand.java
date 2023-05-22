package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CliDefinition;
import de.arthurpicht.cli.command.CommandParserResult;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.console.Console;
import de.arthurpicht.console.message.MessageBuilder;
import de.arthurpicht.console.message.format.Format;
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
        String executableName = call.getCliDefinition().getCliDescription().getExecutableName();

        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.addText("Usage: ");
        messageBuilder.addText(executableName, Format.BRIGHT_WHITE_TEXT());

        if (definition.hasGlobalOptions()) {
            messageBuilder.addText(" [global options]");
        }

        String commandList = " " + Strings.listing(call.getCommandList(), " ");
        messageBuilder.addText(commandList, Format.BRIGHT_WHITE_TEXT());

        if (commandParserResult.hasSpecificOptions()) {
            messageBuilder.addText(" [specific options]");
        }

        if (commandParserResult.hasParameters()) {
            String helpUsage = " " + commandParserResult.getParameters().getHelpUsageSubString();
            messageBuilder.addText(helpUsage, Format.BRIGHT_WHITE_TEXT());
        }

        Console.out(messageBuilder.build());
    }

    private void printDescription(CommandParserResult commandParserResult) {
        if (commandParserResult.hasDescription()) {
            String description = HelpFormatterCommons.indentString(commandParserResult.getDescription());
            Console.println(description);
        }
    }

    private void printGlobalOptions(CliDefinition cliDefinition) {
        if (cliDefinition.hasGlobalOptions()) {
            Options globalOptions = cliDefinition.getGlobalOptions();
            Printer.printOptions(globalOptions, "Global Options:");
        }
    }

    private void printSpecificOptions(CommandParserResult commandParserResult) {
        if (commandParserResult.hasSpecificOptions()) {
            Options specificOptions = commandParserResult.getSpecificOptions();
            Printer.printOptions(specificOptions, "Specific options:");
        }
    }

    private void printParameters(CommandParserResult commandParserResult) {
        if (commandParserResult.hasParameters()) {
            Parameters parameters = commandParserResult.getParameters();
            Printer.printParameters(parameters);
        }
    }

}
