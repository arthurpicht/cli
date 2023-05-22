package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CliDefinition;
import de.arthurpicht.cli.CliDescription;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.tree.CommandTerminator;
import de.arthurpicht.cli.command.tree.CommandTree;
import de.arthurpicht.cli.command.tree.CommandTreeNode;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.cli.print.UsageMessage;
import de.arthurpicht.console.Console;
import de.arthurpicht.console.message.Message;

import java.util.List;

public class HelpFormatterMan {

    public void out(CliCall cliCall) {

        CliDefinition cliDefinition = cliCall.getCliDefinition();
        CliDescription cliDescription = cliDefinition.getCliDescription();
        CommandTree commandTree = cliCall.getCliDefinition().getCommandTree();

        Printer.printHeaderString(cliDefinition);

        Printer.printExecutableDescription(cliDescription);

        Printer.printUsage(cliDefinition, commandTree);

        if (cliDefinition.hasGlobalOptions()) {
            Options globalOptions = cliDefinition.getGlobalOptions();
            Printer.printOptions(globalOptions, "Global Options:");
        }

        printCommandBlocks(cliDefinition, commandTree);

    }

    private void printCommandBlocks(CliDefinition cliDefinition, CommandTree commandTree) {

        if (cliDefinition.hasDefaultCommandToBeIncludedIntoHelpText()) {
            Console.println();
            Console.print("Usage: ");
            Console.out(UsageMessage.getUsageOfDefaultCommand(cliDefinition, false, false));
            DefaultCommand defaultCommand = cliDefinition.getDefaultCommand();
            if (defaultCommand.hasParameters()) {
                Parameters parameters = defaultCommand.getParameters();
                Printer.printParameters(parameters);
            }
        }

        if (commandTree.hasCommands()) {
            List<CommandTreeNode> terminatedNodes = commandTree.getTerminatedNodesSorted();
            for (CommandTreeNode commandTreeNode : terminatedNodes) {
                printCommandBlock(commandTreeNode, cliDefinition);
            }
        }
    }

    private void printCommandBlock(CommandTreeNode commandTreeNode, CliDefinition cliDefinition) {

        Console.println();
        Message usageMessage = UsageMessage.getCommandSpecificUsage(commandTreeNode, cliDefinition, false, "Usage: ");
        Console.out(usageMessage);

        CommandTerminator commandTerminator = commandTreeNode.getCommand().getCommandTerminator();

        printCommandDescription(commandTerminator);

        printSpecificOptions(commandTerminator);

        printParameters(commandTerminator);
    }

    private void printCommandDescription(CommandTerminator commandTerminator) {
        if (commandTerminator.hasDescription()) {
            Console.println(HelpFormatterCommons.indentString(commandTerminator.getDescription()));
        }
    }

    private void printSpecificOptions(CommandTerminator commandTerminator) {
        if (commandTerminator.hasSpecificOptions()) {
            Options specificOptions = commandTerminator.getSpecificOptions();
            Printer.printOptions(specificOptions, "Specific options:");
        }
    }

    private void printParameters(CommandTerminator commandTerminator) {
        if (commandTerminator.hasParameters()) {
            Parameters parameters = commandTerminator.getParameters();
            Printer.printParameters(parameters);
        }
    }

}