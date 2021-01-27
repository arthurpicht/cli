package de.arthurpicht.cli.command.tree.generic;

import de.arthurpicht.utils.core.collection.Sets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnsortedMultiBiTreeNodeTest {

    @Test
    public void singleNode() {
        UnsortedMultiBiTreeNode<String> node = new UnsortedMultiBiTreeNode<>("A");
        assertFalse(node.isAttachedToParent());
        assertTrue(node.isRoot());
        assertTrue(node.isLeaf());
        assertFalse(node.hasChildren());
        assertTrue(node.getChildren().isEmpty());
        assertTrue(node.isSingular());
        assertEquals(0, node.getNrOfChildren());
        assertEquals("A", node.getElementStringChain());
    }

    @Test
    public void twoNodes() {
        UnsortedMultiBiTreeNode<String> nodeA = new UnsortedMultiBiTreeNode<>("A");
        UnsortedMultiBiTreeNode<String> nodeB = new UnsortedMultiBiTreeNode<>("B");
        nodeA.addChild(nodeB);
        nodeB.attachToParent(nodeA);

        assertFalse(nodeA.isAttachedToParent());
        assertTrue(nodeA.isRoot());
        assertFalse(nodeA.isLeaf());
        assertTrue(nodeA.hasChildren());
        assertFalse(nodeA.getChildren().isEmpty());
        assertFalse(nodeA.isSingular());
        assertEquals(1, nodeA.getNrOfChildren());
        assertEquals("A", nodeA.getElementStringChain());
        assertEquals("B", Sets.getSomeElement(nodeA.getChildren()).getElement());

        assertTrue(nodeB.isAttachedToParent());
        assertFalse(nodeB.isRoot());
        assertTrue(nodeB.isLeaf());
        assertFalse(nodeB.hasChildren());
        assertTrue(nodeB.getChildren().isEmpty());
        assertFalse(nodeB.isSingular());
        assertEquals(0, nodeB.getNrOfChildren());
        assertEquals("A B", nodeB.getElementStringChain());
    }

    @Test
    public void updateNode() {
        UnsortedMultiBiTreeNode<String> node = new UnsortedMultiBiTreeNode<>("A");
        assertEquals("A", node.getElement());

        node.updateElement("B");
        assertEquals("B", node.getElement());
    }

}