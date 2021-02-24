package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CommandLineInterfaceCall;
import de.arthurpicht.cli.CommandLineInterfaceDefinition;
import de.arthurpicht.cli.CommandLineInterfaceDescription;
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

    public void out(CommandLineInterfaceCall commandLineInterfaceCall) {

        CommandLineInterfaceDefinition commandLineInterfaceDefinition = commandLineInterfaceCall.getCommandLineInterfaceDefinition();
        CommandLineInterfaceDescription commandLineInterfaceDescription = commandLineInterfaceDefinition.getCommandLineInterfaceDescription();
        CommandTree commandTree = commandLineInterfaceCall.getCommandLineInterfaceDefinition().getCommandTree();

        HelpFormatterCommons.printHeaderString(commandLineInterfaceDefinition);

        HelpFormatterCommons.printExecutableDescription(commandLineInterfaceDescription);

        printUsage(commandLineInterfaceDefinition, commandTree);

        if (commandLineInterfaceDefinition.hasGlobalOptions()) {
            CLIContext.out.println("Global Options:");
            Options globalOptions = commandLineInterfaceDefinition.getGlobalOptions();
            CLIContext.out.println(HelpFormatterCommons.indentString(globalOptions.getHelpString()));
        }

        printCommandBlocks(commandLineInterfaceDefinition, commandTree);

    }

    private void printUsage(CommandLineInterfaceDefinition commandLineInterfaceDefinition, CommandTree commandTree) {
        CLIContext.out.println("Usage:");

        if (commandLineInterfaceDefinition.hasDefaultCommand()) {
            CLIContext.out.println(INDENT + getUsageOfDefaultCommand(commandLineInterfaceDefinition, false));
        }

        if (commandTree.hasCommands()) {
            List<CommandTreeNode> terminatedNodes = commandTree.getTerminatedNodesSorted();
            for (CommandTreeNode commandTreeNode : terminatedNodes) {
                String usageString = HelpFormatterCommons.getCommandSpecificUsage(commandTreeNode, commandLineInterfaceDefinition);
                CLIContext.out.println(INDENT + usageString);
            }
        }
    }

    private void printCommandBlocks(CommandLineInterfaceDefinition commandLineInterfaceDefinition, CommandTree commandTree) {

        if (commandLineInterfaceDefinition.hasDefaultCommand()) {
            CLIContext.out.println();
            CLIContext.out.println("Usage: " + getUsageOfDefaultCommand(commandLineInterfaceDefinition, false));
            DefaultCommand defaultCommand = commandLineInterfaceDefinition.getDefaultCommand();
            if (defaultCommand.hasParameters()) {
                CLIContext.out.println("Parameters:");
                Parameters parameters = defaultCommand.getParameters();
                CLIContext.out.println(HelpFormatterCommons.indentString(parameters.getHelpString()));
            }
        }

        if (commandTree.hasCommands()) {
            List<CommandTreeNode> terminatedNodes = commandTree.getTerminatedNodesSorted();
            for (CommandTreeNode commandTreeNode : terminatedNodes) {
                printCommandBlock(commandTreeNode, commandLineInterfaceDefinition);
            }
        }
    }

    private void printCommandBlock(CommandTreeNode commandTreeNode, CommandLineInterfaceDefinition commandLineInterfaceDefinition) {

        CLIContext.out.println();
        String usageString = HelpFormatterCommons.getCommandSpecificUsage(commandTreeNode, commandLineInterfaceDefinition);
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