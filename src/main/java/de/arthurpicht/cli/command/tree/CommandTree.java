package de.arthurpicht.cli.command.tree;

import de.arthurpicht.cli.command.tree.generic.UnsortedMultiBiTree;
import de.arthurpicht.cli.command.tree.generic.UnsortedMultiBiTreeNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public boolean hasCommands() {
        return !isEmpty();
    }

    public CommandTreeNode getNode(List<String> commandList) {
        CommandTreeNode commandTreeNode = this.getRootNode();
        for (String commandString : commandList) {
            commandTreeNode = commandTreeNode.getChildByString(commandString);
        }
        return commandTreeNode;
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

    public List<CommandTreeNode> getTerminatedNodesSorted() {
        List<CommandTreeNode> commandTreeNodes = new ArrayList<>(getTerminatedNodes());
        commandTreeNodes.sort(new CommandTreeNodeComparator());
        return commandTreeNodes;
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
