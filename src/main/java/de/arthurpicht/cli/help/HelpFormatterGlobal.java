package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CommandLineInterfaceCall;
import de.arthurpicht.cli.CommandLineInterfaceDefinition;
import de.arthurpicht.cli.command.tree.CommandTree;
import de.arthurpicht.cli.command.tree.CommandTreeNode;
import de.arthurpicht.cli.parameter.Parameters;

import java.util.Set;

public class HelpFormatterGlobal {

    public static void out(CommandLineInterfaceCall commandLineInterfaceCall) {

        CommandLineInterfaceDefinition commandLineInterfaceDefinition = commandLineInterfaceCall.getCommandLineInterfaceDefinition();
        CommandTree commandTree = commandLineInterfaceCall.getCommandLineInterfaceDefinition().getCommandTree();
        Set<CommandTreeNode> terminatedNodes = commandTree.getTerminatedNodes();

        for (CommandTreeNode commandTreeNode : terminatedNodes) {
            String usageString = getUsage(commandTreeNode, commandLineInterfaceDefinition);
            System.out.println(usageString);
        }
    }

    private static void printDescription(CommandLineInterfaceDefinition commandLineInterfaceDefinition) {
        System.out.println(commandLineInterfaceDefinition.getCommandLineInterfaceDescription().getDescription());
    }


    private static String getUsage(CommandTreeNode commandTreeNode, CommandLineInterfaceDefinition cliDefinition) {

        String executableName = cliDefinition.getCommandLineInterfaceDescription().getExecutableName();
        boolean hasGlobalOptions = cliDefinition.hasGlobalOptions();
        String commandsString = commandTreeNode.getElementStringChain();
        boolean hasSpecificOptions = commandTreeNode.getCommand().getCommandTerminator().hasSpecificOptions();
        boolean hasParameters = commandTreeNode.getCommand().getCommandTerminator().hasParameters();

        String usage = executableName;

        if (hasGlobalOptions) usage += " [global options]";

        usage += " " + commandsString;

        if (hasSpecificOptions) usage += " [specific options]";

        if (hasParameters) {
            Parameters parameters = commandTreeNode.getCommand().getCommandTerminator().getParameters();
            usage += " " + parameters.getHelpUsageSubString();
        }

        return usage;
    }

}
