package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CliDefinition;
import de.arthurpicht.cli.CliDescription;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.tree.CommandTerminator;
import de.arthurpicht.cli.command.tree.CommandTree;
import de.arthurpicht.cli.command.tree.CommandTreeNode;
import de.arthurpicht.cli.common.CLIContext;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;

import java.util.List;

import static de.arthurpicht.cli.help.HelpFormatterCommons.INDENT;
import static de.arthurpicht.cli.help.HelpFormatterCommons.getUsageOfDefaultCommand;

public class HelpFormatterMan {

    public void out(CliCall cliCall) {

        CliDefinition cliDefinition = cliCall.getCliDefinition();
        CliDescription cliDescription = cliDefinition.getCliDescription();
        CommandTree commandTree = cliCall.getCliDefinition().getCommandTree();

        HelpFormatterCommons.printHeaderString(cliDefinition);

        HelpFormatterCommons.printExecutableDescription(cliDescription);

        printUsage(cliDefinition, commandTree);

        if (cliDefinition.hasGlobalOptions()) {
            CLIContext.out.println("Global Options:");
            Options globalOptions = cliDefinition.getGlobalOptions();
            CLIContext.out.println(HelpFormatterCommons.indentString(globalOptions.getHelpString()));
        }

        printCommandBlocks(cliDefinition, commandTree);

    }

    private void printUsage(CliDefinition cliDefinition, CommandTree commandTree) {
        CLIContext.out.println("Usage:");

        if (cliDefinition.hasDefaultCommand()) {
            CLIContext.out.println(INDENT + getUsageOfDefaultCommand(cliDefinition, false));
        }

        if (commandTree.hasCommands()) {
            List<CommandTreeNode> terminatedNodes = commandTree.getTerminatedNodesSorted();
            for (CommandTreeNode commandTreeNode : terminatedNodes) {
                String usageString = HelpFormatterCommons.getCommandSpecificUsage(commandTreeNode, cliDefinition);
                CLIContext.out.println(INDENT + usageString);
            }
        }
    }

    private void printCommandBlocks(CliDefinition cliDefinition, CommandTree commandTree) {

        if (cliDefinition.hasDefaultCommand()) {
            CLIContext.out.println();
            CLIContext.out.println("Usage: " + getUsageOfDefaultCommand(cliDefinition, false));
            DefaultCommand defaultCommand = cliDefinition.getDefaultCommand();
            if (defaultCommand.hasParameters()) {
                CLIContext.out.println("Parameters:");
                Parameters parameters = defaultCommand.getParameters();
                CLIContext.out.println(HelpFormatterCommons.indentString(parameters.getHelpString()));
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

        CLIContext.out.println();
        String usageString = HelpFormatterCommons.getCommandSpecificUsage(commandTreeNode, cliDefinition);
        CLIContext.out.println("Usage: " + usageString);

        CommandTerminator commandTerminator = commandTreeNode.getCommand().getCommandTerminator();

        printCommandDescription(commandTerminator);

        printSpecificOptions(commandTerminator);

        printParameters(commandTerminator);
    }

    private void printCommandDescription(CommandTerminator commandTerminator) {
        if (commandTerminator.hasDescription()) {
            CLIContext.out.println(HelpFormatterCommons.indentString(commandTerminator.getDescription()));
        }
    }

    private void printSpecificOptions(CommandTerminator commandTerminator) {
        if (commandTerminator.hasSpecificOptions()) {
            CLIContext.out.println("Specific options:");
            Options specificOptions = commandTerminator.getSpecificOptions();
            CLIContext.out.println(HelpFormatterCommons.indentString(specificOptions.getHelpString()));
        }
    }

    private void printParameters(CommandTerminator commandTerminator) {
        if (commandTerminator.hasParameters()) {
            CLIContext.out.println("Parameters:");
            Parameters parameters = commandTerminator.getParameters();
            CLIContext.out.println(HelpFormatterCommons.indentString(parameters.getHelpString()));
        }
    }

}