package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CliDefinition;
import de.arthurpicht.cli.CliDescription;
import de.arthurpicht.cli.command.tree.CommandTree;
import de.arthurpicht.cli.command.tree.CommandTreeNode;
import de.arthurpicht.cli.common.CLIContext;
import de.arthurpicht.cli.option.Option;
import de.arthurpicht.cli.option.OptionComparator;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.print.OptionMessage;
import de.arthurpicht.console.Console;
import de.arthurpicht.console.message.Message;
import de.arthurpicht.console.message.format.Format;

import java.util.ArrayList;
import java.util.List;

import static de.arthurpicht.cli.help.HelpFormatterCommons.INDENT;
import static de.arthurpicht.cli.help.HelpFormatterCommons.getUsageOfDefaultCommand;

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
        CLIContext.out.println("Usage:");

        if (cliDefinition.hasDefaultCommandToBeIncludedIntoHelpText()) {
            CLIContext.out.println(INDENT + getUsageOfDefaultCommand(cliDefinition, false));
        }

        if (commandTree.hasCommands()) {
            List<CommandTreeNode> terminatedNodes = commandTree.getTerminatedNodesSorted();
            for (CommandTreeNode commandTreeNode : terminatedNodes) {
                Message usageMessage = HelpFormatterCommons.getCommandSpecificUsage(commandTreeNode, cliDefinition, true, "");
                Console.out(usageMessage);
            }
        }
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

}
