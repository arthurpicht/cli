package de.arthurpicht.cli.command.tree;

import de.arthurpicht.cli.command.tree.generic.UnsortedMultiBiTree;
import de.arthurpicht.cli.command.tree.generic.UnsortedMultiBiTreeNode;

import java.util.HashSet;
import java.util.Set;

public class CommandTree extends UnsortedMultiBiTree<Command> {

    public CommandTree() {
        super(new CommandTreeNode(new OneCommand("root")));
    }

    public CommandTreeNode getRootNode() {
        return (CommandTreeNode) super.getRootNode();
    }

    public boolean isEmpty() {
        return !this.getRootNode().hasChildren();
    }

    public Set<CommandTreeNode> getTerminatedNodes() {
        Set<CommandTreeNode> terminatedNodes = new HashSet<>();
        Set<UnsortedMultiBiTreeNode<Command>> allNodes = getAllNodes();
        for (UnsortedMultiBiTreeNode<Command> node : allNodes) {
            CommandTreeNode commandTreeNode = (CommandTreeNode) node;
            if (commandTreeNode.isTerminated()) terminatedNodes.add(commandTreeNode);
        }
        return terminatedNodes;
    }

    public Set<String> getAllCommandChainStrings() {
        Set<CommandTreeNode> terminatedNodes = getTerminatedNodes();
        Set<String> commandChains = new HashSet<>();

        for (CommandTreeNode terminatedRoot : terminatedNodes) {
            commandChains.add(terminatedRoot.getElementStringChain());
        }

        return commandChains;
    }

}
