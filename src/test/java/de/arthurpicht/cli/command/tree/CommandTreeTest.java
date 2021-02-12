package de.arthurpicht.cli.command.tree;

import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandTreeTest {

    @Test
    public void empty() {
        CommandTree commandTree = new CommandTree();
        assertTrue(commandTree.isEmpty());
    }

    @Test
    public void getRootNode() {
        CommandTree commandTree = new CommandTree();
        CommandTreeNode commandTreeNode = commandTree.getRootNode();
        assertEquals("root", commandTreeNode.getCommand().getCommandString());
    }

    @Test
    public void getNode_Empty() {
        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C").build());
        commands.add(new CommandSequenceBuilder().addCommands("A", "X", "Y").build());
        CommandTree commandTree = commands.getCommandTree();

        CommandTreeNode commandTreeNode = commandTree.getNode(new ArrayList<>());

        assertEquals(commandTree.getRootNode(), commandTreeNode);
    }

    @Test
    public void getNode_FirstLevel() {
        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C").build());
        commands.add(new CommandSequenceBuilder().addCommands("A", "X", "Y").build());
        CommandTree commandTree = commands.getCommandTree();

        CommandTreeNode commandTreeNode = commandTree.getNode(Collections.singletonList("A"));

        assertEquals("A", commandTreeNode.getCommand().getCommandString());
        assertEquals(2, commandTreeNode.getCommandTreeNodeChildren().size());
    }

    @Test
    public void getNode_SecondLevel() {
        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C").build());
        commands.add(new CommandSequenceBuilder().addCommands("A", "X", "Y").build());
        CommandTree commandTree = commands.getCommandTree();

        CommandTreeNode commandTreeNode = commandTree.getNode(Arrays.asList("A", "B"));

        assertEquals("B", commandTreeNode.getCommand().getCommandString());
        assertEquals(1, commandTreeNode.getCommandTreeNodeChildren().size());
    }

    @Test
    public void getNode_LeafLevel() {
        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C").build());
        commands.add(new CommandSequenceBuilder().addCommands("A", "X", "Y").build());
        CommandTree commandTree = commands.getCommandTree();

        CommandTreeNode commandTreeNode = commandTree.getNode(Arrays.asList("A", "B", "C"));

        assertEquals("C", commandTreeNode.getCommand().getCommandString());
        assertEquals(0, commandTreeNode.getCommandTreeNodeChildren().size());
    }




}