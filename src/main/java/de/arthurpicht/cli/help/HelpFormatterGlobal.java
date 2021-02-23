package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CommandLineInterfaceCall;
import de.arthurpicht.cli.CommandLineInterfaceDefinition;
import de.arthurpicht.cli.CommandLineInterfaceDescription;
import de.arthurpicht.cli.command.tree.CommandTree;
import de.arthurpicht.cli.command.tree.CommandTreeNode;
import de.arthurpicht.cli.option.Options;

import java.util.Set;

import static de.arthurpicht.cli.help.HelpFormatterCommons.INDENT;
import static de.arthurpicht.cli.help.HelpFormatterCommons.getUsageOfDefaultCommand;

public class HelpFormatterGlobal {

    public void out(CommandLineInterfaceCall commandLineInterfaceCall) {

        CommandLineInterfaceDefinition commandLineInterfaceDefinition = commandLineInterfaceCall.getCommandLineInterfaceDefinition();
        CommandLineInterfaceDescription commandLineInterfaceDescription = commandLineInterfaceDefinition.getCommandLineInterfaceDescription();
        CommandTree commandTree = commandLineInterfaceCall.getCommandLineInterfaceDefinition().getCommandTree();
        Set<CommandTreeNode> terminatedNodes = commandTree.getTerminatedNodes();

        HelpFormatterCommons.printHeaderString(commandLineInterfaceDefinition);

        HelpFormatterCommons.printExecutableDescription(commandLineInterfaceDescription);

        printUsage(commandLineInterfaceDefinition, commandTree);

        if (commandLineInterfaceDefinition.hasGlobalOptions()) {
            System.out.println("Global Options:");
            Options globalOptions = commandLineInterfaceDefinition.getGlobalOptions();
            System.out.println(HelpFormatterCommons.indentString(globalOptions.getHelpString()));
        }
    }

    private void printUsage(CommandLineInterfaceDefinition commandLineInterfaceDefinition, CommandTree commandTree) {
        System.out.println("Usage:");

        if (commandLineInterfaceDefinition.hasDefaultCommand()) {
            System.out.println(INDENT + getUsageOfDefaultCommand(commandLineInterfaceDefinition, false));
        }

        if (commandTree.hasCommands()) {
            Set<CommandTreeNode> terminatedNodes = commandTree.getTerminatedNodes();
            for (CommandTreeNode commandTreeNode : terminatedNodes) {
                String usageString = HelpFormatterCommons.getCommandSpecificUsage(commandTreeNode, commandLineInterfaceDefinition);
                System.out.println(INDENT + usageString);
            }
        }
    }

}
