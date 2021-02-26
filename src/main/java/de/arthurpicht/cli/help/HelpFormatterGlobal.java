package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CliDefinition;
import de.arthurpicht.cli.CliDescription;
import de.arthurpicht.cli.command.tree.CommandTree;
import de.arthurpicht.cli.command.tree.CommandTreeNode;
import de.arthurpicht.cli.common.CLIContext;
import de.arthurpicht.cli.option.Options;

import java.util.List;

import static de.arthurpicht.cli.help.HelpFormatterCommons.INDENT;
import static de.arthurpicht.cli.help.HelpFormatterCommons.getUsageOfDefaultCommand;

public class HelpFormatterGlobal {

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
    }

    private void printUsage(CliDefinition cliDefinition, CommandTree commandTree) {
        CLIContext.out.println("Usage:");

        if (hasDefaultCommandToBeIncludedIntoHelpText(cliDefinition)) {
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

    private boolean hasDefaultCommandToBeIncludedIntoHelpText(CliDefinition cliDefinition) {
        return cliDefinition.hasDefaultCommand() && cliDefinition.getDefaultCommand().includeIntoHelpText();
    }

}
