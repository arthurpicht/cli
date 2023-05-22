package de.arthurpicht.cli.common;

import de.arthurpicht.cli.command.tree.CommandTerminator;
import de.arthurpicht.cli.command.tree.CommandTree;
import de.arthurpicht.cli.command.tree.CommandTreeNode;
import de.arthurpicht.cli.option.HelpOption;
import de.arthurpicht.cli.option.Options;

import java.util.Set;

public class PostProcessor {

    public static void addAutoHelp(Options optionsGlobal, CommandTree commandTree) {
        if (optionsGlobal == null) optionsGlobal = new Options();
        addHelpOptionToGlobalOptions(optionsGlobal);
        addHelpOptionsToTerminatedCommands(commandTree);
    }

    private static void addHelpOptionToGlobalOptions(Options optionsGlobal) {
        if (notHasHelpOption(optionsGlobal)) {
            optionsGlobal.add(new HelpOption());
        }
    }

    private static void addHelpOptionsToTerminatedCommands(CommandTree commandTree) {
        Set<CommandTreeNode> terminatedNodes = commandTree.getTerminatedNodes();
        for (CommandTreeNode node : terminatedNodes) {
            CommandTerminator commandTerminator = node.getCommand().getCommandTerminator();
            Options specificOptions = node.getCommand().getCommandTerminator().getSpecificOptions();
            if (notHasHelpOption(specificOptions)) {
                specificOptions.add(new HelpOption());
            }
        }
    }

    private static boolean notHasHelpOption(Options options) {
        return !options.hasOptionWithId(HelpOption.ID);
    }

}
