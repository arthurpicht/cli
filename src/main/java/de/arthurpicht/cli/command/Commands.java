package de.arthurpicht.cli.command;

import de.arthurpicht.cli.command.exceptions.CommandSpecException;
import de.arthurpicht.cli.command.tree.*;

import java.util.Set;

/**
 * End user class intended for specifying command sequences with respective
 * specific parameters and arguments.
 *
 * Specification is restricted in the following manner. Violations will result in a {@link CommandSpecException} :
 * <ul>
 *     <li>A sequence of commands can not be specified twice.</li>
 *     <li>An open command may only be specified at the end of a command sequence. (realized in {@link CommandSequenceBuilder})</li>
 *     <li>After an open command there must be no further command. (realized in {@link CommandSequenceBuilder})</li>
 *     <lid>A combination of an open command and parameters is not possible. (realized in {@link CommandSequenceBuilder})</lid>
 * </ul>
 *
 *
 */
public class Commands {

    // TODO Take measures for sub-sequences ("--")

    private final CommandTree commandTree;

    public Commands() {
        this.commandTree = new CommandTree();
    }

    public CommandTree getCommandTree() {
        return this.commandTree;
    }

    public Commands add(CommandSequence commandSequence) {
        CongruenceChecker congruenceChecker = new CongruenceChecker(this.commandTree, commandSequence);

        if (congruenceChecker.isSequencePredefined()) {
            throw new CommandSpecException("Command sequence is already defined.");
        }

        if (congruenceChecker.isCompletelyCongruent()) {
            Command lastCommandInSequence = commandSequence.getLastCommand();
            CommandTreeNode lastCongruentNode = congruenceChecker.getLastCongruentNode();
            lastCongruentNode.getCommand().terminate(lastCommandInSequence.getCommandTerminator());
            return this;
        }

        CommandSequenceIterator commandSequenceIterator =
                new CommandSequenceIterator(commandSequence, congruenceChecker.getLastCongruentSequenceIndex());
        CommandTreeNode indexNode = congruenceChecker.getLastCongruentNode();
        while (commandSequenceIterator.hasNext()) {
            Command command = commandSequenceIterator.getNext();
            CommandTreeNode newNode = new CommandTreeNode(command);
            indexNode.addChild(newNode);
            newNode.attachToParent(indexNode);
            indexNode = newNode;
        }

        return this;
    }

    public boolean isEmpty() {
        return this.commandTree.isEmpty();
    }

    public Set<String> getCommandChainStrings() {
        return this.getCommandTree().getAllCommandChainStrings();
    }

    public static boolean hasDefinitions(Commands commands) {
        return !commands.isEmpty();
    }

}
