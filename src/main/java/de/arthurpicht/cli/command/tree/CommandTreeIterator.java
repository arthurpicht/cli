package de.arthurpicht.cli.command.tree;

import de.arthurpicht.cli.command.Commands;

import java.util.Set;

public class CommandTreeIterator {

    private final CommandTree commandTree;
    private CommandTreeNode currentNode;

    public CommandTreeIterator(CommandTree commandTree) {
        this.commandTree = commandTree;
        this.currentNode = this.commandTree.getRootNode();
    }

    public CommandTreeIterator(Commands commands) {
        this.commandTree = commands.getCommandTree();
        this.currentNode = this.commandTree.getRootNode();
    }

    public boolean hasNext() {
        return this.currentNode.hasChildren();
    }

    public Set<Command> getCommandCandidates() {
        return this.currentNode.getChildrenCommands();
    }

    public void stepForward(Command command) {
        if (!this.currentNode.hasChildWithCommand(command))
            throw new IllegalArgumentException("Specified command not found as child of current node.");
        this.currentNode = this.currentNode.getChildByCommand(command);
    }

}
