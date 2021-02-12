package de.arthurpicht.cli.help;

import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.tree.CommandTree;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.List;

public class HelpFormatter {

    private CommandTree commandTree;

    public HelpFormatter(Commands commands) {
        this.commandTree = commands.getCommandTree();
    }

    public void out(List<String> commandList) {
        System.out.println("usage: " + Strings.listing(commandList, " "));

    }

}
