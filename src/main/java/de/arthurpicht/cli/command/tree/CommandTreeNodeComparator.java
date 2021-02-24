package de.arthurpicht.cli.command.tree;

import java.util.Comparator;

/**
 * Comparator for imposing an order on terminated {@link CommandTreeNode}s.
 * Two order criteria are applied: HelpPriority as a first criterion, and in case of equal
 * help priority alphabetical order of the commands string as a second criterion.
 */
public class CommandTreeNodeComparator implements Comparator<CommandTreeNode> {

    @Override
    public int compare(CommandTreeNode commandTreeNode1, CommandTreeNode commandTreeNode2) {

        if (!commandTreeNode1.isTerminated() || !commandTreeNode2.isTerminated())
            throw new IllegalArgumentException("Comparison can only performed on terminated nodes.");

        int helpPriority1 = commandTreeNode1.getCommand().getCommandTerminator().getHelpPriority();
        int helpPriority2 = commandTreeNode2.getCommand().getCommandTerminator().getHelpPriority();

        if (helpPriority1 > helpPriority2) return 1;
        if (helpPriority1 < helpPriority2) return -1;

        String commandString1 = commandTreeNode1.getCommandsString();
        String commandString2 = commandTreeNode2.getCommandsString();

        return String.CASE_INSENSITIVE_ORDER.compare(commandString1, commandString2);
    }
}
