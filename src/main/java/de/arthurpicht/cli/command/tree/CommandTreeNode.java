package de.arthurpicht.cli.command.tree;

import de.arthurpicht.cli.command.tree.generic.UnsortedMultiBiTreeNode;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.*;

public class CommandTreeNode extends UnsortedMultiBiTreeNode<Command> {

    public CommandTreeNode(Command command) {
        super(command);
    }

    public Command getCommand() {
        return this.getElement();
    }

    public boolean isInitial() {
        return this.getParent().isRoot();
    }

    public CommandTreeNode getParent() {
        return (CommandTreeNode) super.getParent();
    }

    public void attachToParent(CommandTreeNode parent) {
        super.attachToParent(parent);
    }

    public void addChild(CommandTreeNode child) {
        super.addChild(child);
    }

    public void removeChild(CommandTreeNode child) {
        super.removeChild(child);
    }

    public boolean hasChild(CommandTreeNode childNode) {
        if (!childNode.isAttachedToParent()) return false;
        CommandTreeNode parentOfChildNode = childNode.getParent();
        return parentOfChildNode.equals(this);
    }

    public boolean hasChildWithCommand(Command command) {
        for (CommandTreeNode commandTreeNode : this.getCommandTreeNodeChildren()) {
            if (commandTreeNode.getCommand().equals(command)) return true;
        }
        return false;
    }

    public boolean hasChildWithCommandString(String commandString) {
        for (CommandTreeNode commandTreeNode : this.getCommandTreeNodeChildren()) {
            if (commandTreeNode.getCommand().asString().equals(commandString)) return true;
        }
        return false;
    }

    public CommandTreeNode getChildByCommand(Command command) {
        for (CommandTreeNode commandTreeNode : this.getCommandTreeNodeChildren()) {
            if (commandTreeNode.getCommand().equals(command)) return commandTreeNode;
        }
        throw new IllegalStateException("No CommandTreeNode as child with specified command.");
    }

    public CommandTreeNode getChildByString(String commandString) {
        for (CommandTreeNode commandTreeNode : this.getCommandTreeNodeChildren()) {
            if (commandTreeNode.getCommand().getCommandString().equals(commandString)) return commandTreeNode;
        }
        throw new IllegalStateException("No CommandTreeNode as child with specified command string.");
    }


    public boolean isTerminated() {
        return this.getCommand().isTerminated();
    }

    public boolean isSubsequence() {
        return isTerminated() && !isLeaf();
    }

    public Set<CommandTreeNode> getCommandTreeNodeChildren() {
        Set<UnsortedMultiBiTreeNode<Command>> superChildren = super.getChildren();
        Set<CommandTreeNode> children = new HashSet<>();
        for (UnsortedMultiBiTreeNode<Command> superChild : superChildren) {
            children.add((CommandTreeNode) superChild);
        }
        return children;
    }

    public Set<Command> getChildrenCommands() {
        Set<CommandTreeNode> children = getCommandTreeNodeChildren();
        Set<Command> commands = new HashSet<>();
        for (CommandTreeNode commandTreeNode : children) {
            commands.add(commandTreeNode.getCommand());
        }
        return commands;
    }

    public static void attach(CommandTreeNode parent, CommandTreeNode child) {
        parent.addChild(child);
        child.attachToParent(parent);
    }

    public String getCommandsString() {
        List<String> commandChainList = new ArrayList<>();
        commandChainList.add(getCommand().toString());
        CommandTreeNode node = this;
        while(!node.getParent().isRoot()) {
            node = node.getParent();
            commandChainList.add(node.getElement().toString());
        }

        Collections.reverse(commandChainList);
        return Strings.listing(commandChainList, " ");
    }

}
