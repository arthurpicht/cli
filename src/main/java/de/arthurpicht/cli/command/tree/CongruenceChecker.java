package de.arthurpicht.cli.command.tree;

import de.arthurpicht.cli.command.CommandSequence;

import java.util.List;

public class CongruenceChecker {

    private final CommandSequence commandSequence;
    private CommandTreeNode lastCongruentNode;
    private int lastCongruentSequenceIndex = -1;

    public CongruenceChecker(CommandTree commandTree, CommandSequence commandSequence) {
        this.commandSequence = commandSequence;
        this.lastCongruentNode = commandTree.getRootNode();

        List<Command> commandList = commandSequence.getCommandList();
        for (int i = 0; i < commandList.size(); i++) {
            Command command = commandList.get(i);
            if (this.lastCongruentNode.hasChildWithCommand(command)) {
                this.lastCongruentSequenceIndex = i;
                this.lastCongruentNode = this.lastCongruentNode.getChildByCommand(command);
            } else {
                break;
            }
        }
    }

    public int getLastCongruentSequenceIndex() {
        return this.lastCongruentSequenceIndex;
    }

    public CommandTreeNode getLastCongruentNode() {
        return this.lastCongruentNode;
    }

    public boolean isCompletelyCongruent() {
        return (this.commandSequence.getCommandList().size() - 1 == this.lastCongruentSequenceIndex);
    }

    public boolean isSequencePredefined() {
        return isCompletelyCongruent() && getLastCongruentNode().isTerminated();
    }

}
