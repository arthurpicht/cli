package de.arthurpicht.cli.command.tree.generic;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnsortedMultiBiTreeTest {

    @Test
    public void rootNode() {
        UnsortedMultiBiTree<String> tree
                = new UnsortedMultiBiTree<>(new UnsortedMultiBiTreeNode<>("root"));
        UnsortedMultiBiTreeNode<String> node = tree.getRootNode();

        assertEquals("root", node.getElement());
    }

    @Test
    public void getAllNodes() {
        UnsortedMultiBiTreeNode<String> rootNode = new UnsortedMultiBiTreeNode<>("root");
        UnsortedMultiBiTree<String> tree
                = new UnsortedMultiBiTree<>(rootNode);

        UnsortedMultiBiTreeNode<String> nodeA = new UnsortedMultiBiTreeNode<>("A");
        rootNode.addChild(nodeA);
        nodeA.attachToParent(rootNode);

        UnsortedMultiBiTreeNode<String> nodeB = new UnsortedMultiBiTreeNode<>("B");
        nodeA.addChild(nodeB);
        nodeB.attachToParent(nodeA);

        UnsortedMultiBiTreeNode<String> nodeC = new UnsortedMultiBiTreeNode<>("C");
        nodeB.addChild(nodeC);
        nodeC.attachToParent(nodeB);

        Set<UnsortedMultiBiTreeNode<String>> nodes = tree.getAllNodes();
        assertEquals(4, nodes.size());
    }


}