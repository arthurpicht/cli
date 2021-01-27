package de.arthurpicht.cli.command.tree.generic;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class UnsortedMultiBiTree<E> {

    private final UnsortedMultiBiTreeNode<E> rootNode;

    public UnsortedMultiBiTree(UnsortedMultiBiTreeNode<E> rootNode) {
        this.rootNode = rootNode;
    }

    public UnsortedMultiBiTreeNode<E> getRootNode() {
        return this.rootNode;
    }

    public boolean isEmpty() {
        return !this.rootNode.hasChildren();
    }

    public Set<UnsortedMultiBiTreeNode<E>> getAllNodes() {
        Set<UnsortedMultiBiTreeNode<E>> nodes = new HashSet<>();

        Stack<UnsortedMultiBiTreeNode<E>> stack = new Stack<>();
        stack.push(this.rootNode);

        while (!stack.empty()) {
            UnsortedMultiBiTreeNode<E> node = stack.pop();
            nodes.add(node);
            Set<UnsortedMultiBiTreeNode<E>> children = node.getChildren();
            stack.addAll(children);
        }

        return nodes;
    }

}