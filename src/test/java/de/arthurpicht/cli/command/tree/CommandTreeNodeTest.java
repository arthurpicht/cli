package de.arthurpicht.cli.command.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CommandTreeNodeTest {

    @Test
    public void simple() {
        Command command = new OneCommand("Test");
        CommandTreeNode commandTreeNode = new CommandTreeNode(command);

        assertEquals(command, commandTreeNode.getCommand());
        assertTrue(commandTreeNode.isLeaf());
        assertEquals(0, commandTreeNode.getNrOfChildren());
        assertFalse(commandTreeNode.isAttachedToParent());
    }

    @Test
    public void hasChild() {
        Command command = new OneCommand("Test");
        CommandTreeNode commandTreeNode = new CommandTreeNode(command);

        Command commandA = new OneCommand("A");
        CommandTreeNode commandTreeNodeA = new CommandTreeNode(commandA);

        assertFalse(commandTreeNode.hasChild(commandTreeNodeA));

        commandTreeNode.addChild(commandTreeNodeA);
        commandTreeNodeA.attachToParent(commandTreeNode);

        assertTrue(commandTreeNode.hasChild(commandTreeNodeA));
    }

    @Test
    public void equals1() {

        Command command = new OneCommand("Test");
        CommandTreeNode commandTreeNode = new CommandTreeNode(command);

        Command commandA = new OneCommand("A");
        CommandTreeNode commandTreeNodeA = new CommandTreeNode(commandA);
        commandTreeNode.addChild(commandTreeNodeA);
        commandTreeNodeA.attachToParent(commandTreeNode);

        Command commandB = new OneCommand("B");
        CommandTreeNode commandTreeNodeB = new CommandTreeNode(commandB);
        commandTreeNode.addChild(commandTreeNodeB);
        commandTreeNodeB.attachToParent(commandTreeNode);

        assertEquals(2, commandTreeNode.getNrOfChildren());
        assertTrue(commandTreeNode.hasChild(commandTreeNodeA));
        assertTrue(commandTreeNode.hasChild(commandTreeNodeB));

        CommandTreeNode commandTreeNodeFake = new CommandTreeNode(command);
        assertNotEquals(commandTreeNodeFake, commandTreeNode);
    }

    @Test
    public void getCommandsString() {

        Command command = new OneCommand("root");
        CommandTreeNode rootNode = new CommandTreeNode(command);

        Command commandA = new OneCommand("A");
        CommandTreeNode nodeA = new CommandTreeNode(commandA);
        rootNode.addChild(nodeA);
        nodeA.attachToParent(rootNode);

        Command commandB = new OneCommand("B");
        CommandTreeNode nodeB = new CommandTreeNode(commandB);
        nodeA.addChild(nodeB);
        nodeB.attachToParent(nodeA);

        String commandsString = nodeB.getCommandsString();
        assertEquals("A B", commandsString);
    }

}