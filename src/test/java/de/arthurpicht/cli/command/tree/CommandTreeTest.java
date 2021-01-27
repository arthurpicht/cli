package de.arthurpicht.cli.command.tree;

import org.junit.jupiter.api.Test;

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

}