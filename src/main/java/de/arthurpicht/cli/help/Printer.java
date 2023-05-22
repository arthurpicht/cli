package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CliDefinition;
import de.arthurpicht.cli.CliDescription;
import de.arthurpicht.cli.command.tree.CommandTree;
import de.arthurpicht.cli.command.tree.CommandTreeNode;
import de.arthurpicht.cli.option.Option;
import de.arthurpicht.cli.option.OptionComparator;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.cli.print.OptionMessage;
import de.arthurpicht.cli.print.ParametersMessage;
import de.arthurpicht.cli.print.UsageMessage;
import de.arthurpicht.console.Console;
import de.arthurpicht.console.message.Message;
import de.arthurpicht.console.message.format.Format;

import java.util.ArrayList;
import java.util.List;

public class Printer {

    public static void printHeaderString(CliDefinition cliDefinition) {
        String headerString = HelpFormatterCommons.getHeaderString(cliDefinition);
        Console.println(headerString, Format.BRIGHT_WHITE_TEXT());
    }

    public static void printExecutableDescription(CliDescription cliDescription) {
        if (cliDescription.hasDescription()) {
            String description = cliDescription.getDescription();
            Console.println(HelpFormatterCommons.indentString(description));
        }
    }

    public static void printUsage(CliDefinition cliDefinition, CommandTree commandTree) {
        Console.println("Usage:");

        if (cliDefinition.hasDefaultCommandToBeIncludedIntoHelpText()) {
            Message usageMessage = UsageMessage.getUsageOfDefaultCommand(cliDefinition, false, true);
            Console.out(usageMessage);
        }

        if (commandTree.hasCommands()) {
            List<CommandTreeNode> terminatedNodes = commandTree.getTerminatedNodesSorted();
            for (CommandTreeNode commandTreeNode : terminatedNodes) {
                Message usageMessage = UsageMessage.getCommandSpecificUsage(commandTreeNode, cliDefinition, true, "");
                Console.out(usageMessage);
            }
        }
    }

    public static void printUsageDefaultOnly(CliDefinition cliDefinition) {
        Console.println("Usage:");
        Console.out(UsageMessage.getUsageOfDefaultCommand(cliDefinition, true, true));
    }

    public static void printOptions(Options options, String caption) {
        List<Option> orderedOptionList = new ArrayList<>(options.getAllOptions());
        orderedOptionList.sort(new OptionComparator());

        Console.println(caption);
        for (Option option : orderedOptionList) {
            Message message = OptionMessage.asMessage(option);
            Console.out(message);
        }
    }

    public static void printParameters(Parameters parameters) {
        Console.println("Parameters:");
        Message message = ParametersMessage.asMessage(parameters);
        Console.out(message);
    }

}
