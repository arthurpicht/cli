package de.arthurpicht.cli.command.tree;

import de.arthurpicht.cli.command.CommandSequence;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CongruenceCheckerTest {

    private CommandTree setupCommandTree() {
        CommandTree commandTree = new CommandTree();
        CommandTreeNode rootNode = commandTree.getRootNode();

        CommandTreeNode nodeA = new CommandTreeNode(new OneCommand("A"));
        CommandTreeNode.attach(rootNode, nodeA);

        Command commandB = new OneCommand("B");
        commandB.terminate(new CommandTerminator(null, null, null, null));
        CommandTreeNode nodeB = new CommandTreeNode(commandB);
        CommandTreeNode.attach(nodeA, nodeB);

        Command commandC = new OneCommand("C");
        commandC.terminate(new CommandTerminator(null, null, null, null));
        CommandTreeNode nodeC = new CommandTreeNode(commandC);
        CommandTreeNode.attach(nodeB, nodeC);

        return commandTree;
    }

    @Test
    void getLastCongruentNodeIndex_OneCommand() {
        CommandTree commandTree = setupCommandTree();
        CommandSequence commandSequence = new CommandSequenceBuilder().addCommands("A").build();
        CongruenceChecker congruenceChecker = new CongruenceChecker(commandTree, commandSequence);

        assertEquals(0, congruenceChecker.getLastCongruentSequenceIndex());
        assertTrue(congruenceChecker.isCompletelyCongruent());
        assertEquals("A", congruenceChecker.getLastCongruentNode().getCommand().asString());
        assertFalse(congruenceChecker.isSequencePredefined());
    }

    @Test
    void getLastCongruentNodeIndex_TwoCommands() {
        CommandTree commandTree = setupCommandTree();
        CommandSequence commandSequence = new CommandSequenceBuilder().addCommands("A", "B").build();
        CongruenceChecker congruenceChecker = new CongruenceChecker(commandTree, commandSequence);

        assertEquals(1, congruenceChecker.getLastCongruentSequenceIndex());
        assertTrue(congruenceChecker.isCompletelyCongruent());
        assertEquals("B", congruenceChecker.getLastCongruentNode().getCommand().asString());
        assertTrue(congruenceChecker.isSequencePredefined());
    }

    @Test
    void getLastCongruentNodeIndex_ThreeCommands() {
        CommandTree commandTree = setupCommandTree();
        CommandSequence commandSequence = new CommandSequenceBuilder().addCommands("A", "B", "C").build();
        CongruenceChecker congruenceChecker = new CongruenceChecker(commandTree, commandSequence);

        assertEquals(2, congruenceChecker.getLastCongruentSequenceIndex());
        assertTrue(congruenceChecker.isCompletelyCongruent());
        assertEquals("C", congruenceChecker.getLastCongruentNode().getCommand().asString());
        assertTrue(congruenceChecker.isSequencePredefined());
    }

    @Test
    void getLastCongruentNodeIndex_ThreeCommands_OneOver() {
        CommandTree commandTree = setupCommandTree();
        CommandSequence commandSequence = new CommandSequenceBuilder().addCommands("A", "B", "C", "D").build();
        CongruenceChecker congruenceChecker = new CongruenceChecker(commandTree, commandSequence);

        assertEquals(2, congruenceChecker.getLastCongruentSequenceIndex());
        assertFalse(congruenceChecker.isCompletelyCongruent());
        assertEquals("C", congruenceChecker.getLastCongruentNode().getCommand().asString());
        assertFalse(congruenceChecker.isSequencePredefined());
    }

    @Test
    void getLastCongruentNodeIndex_OneCommand_NoCongruence() {
        CommandTree commandTree = setupCommandTree();
        CommandSequence commandSequence = new CommandSequenceBuilder().addCommands("X").build();
        CongruenceChecker congruenceChecker = new CongruenceChecker(commandTree, commandSequence);

        assertEquals(-1, congruenceChecker.getLastCongruentSequenceIndex());
        assertFalse(congruenceChecker.isCompletelyCongruent());
        assertEquals(commandTree.getRootNode(), congruenceChecker.getLastCongruentNode());
        assertFalse(congruenceChecker.isSequencePredefined());
    }

    @Test
    void getLastCongruentNodeIndex_TwoCommands_NoCongruence() {
        CommandTree commandTree = setupCommandTree();
        CommandSequence commandSequence = new CommandSequenceBuilder().addCommands("X", "Y").build();
        CongruenceChecker congruenceChecker = new CongruenceChecker(commandTree, commandSequence);

        assertEquals(-1, congruenceChecker.getLastCongruentSequenceIndex());
        assertFalse(congruenceChecker.isCompletelyCongruent());
        assertEquals(commandTree.getRootNode(), congruenceChecker.getLastCongruentNode());
        assertFalse(congruenceChecker.isSequencePredefined());
    }

}