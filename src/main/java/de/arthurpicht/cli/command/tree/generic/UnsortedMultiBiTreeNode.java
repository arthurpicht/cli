package de.arthurpicht.cli.command.tree.generic;

import de.arthurpicht.utils.core.strings.Strings;

import java.util.*;

public class UnsortedMultiBiTreeNode<E> {

    private final UUID uuid;
    private E element;
    private UnsortedMultiBiTreeNode<E> parent;
    private final Set<UnsortedMultiBiTreeNode<E>> children;

    public UnsortedMultiBiTreeNode(E element) {
        this.uuid = UUID.randomUUID();
        this.element = element;
        this.children = new HashSet<>();
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public E getElement() {
        return this.element;
    }

    public void updateElement(E element) {
        this.element = element;
    }

    public boolean isRoot() {
        return this.getParent() == null;
    }

    public UnsortedMultiBiTreeNode<E> getParent() {
        return this.parent;
    }

    public void attachToParent(UnsortedMultiBiTreeNode<E> parent) {
        this.parent = parent;
    }

    public void detachFromParent() {
        this.parent = null;
    }

    public boolean isAttachedToParent() {
        return this.parent != null;
    }

    public void addChild(UnsortedMultiBiTreeNode<E> child) {
        this.children.add(child);
    }

    public void removeChild(UnsortedMultiBiTreeNode<E> child) {
        this.children.remove(child);
    }

    public int getNrOfChildren() {
        return this.children.size();
    }

    public boolean hasChildren() {
        return !this.children.isEmpty();
    }

    public boolean isSingular() {
        return (isRoot() && isLeaf());
    }

    public boolean hasChild(UnsortedMultiBiTreeNode<E> childNode) {
        if (!childNode.isAttachedToParent()) return false;
        UnsortedMultiBiTreeNode<E> parentOfChildNode = childNode.getParent();
        return parentOfChildNode.equals(this);
    }

    public boolean isLeaf() {
        return this.children.size() == 0;
    }

    public Set<UnsortedMultiBiTreeNode<E>> getChildren() {
        return this.children;
    }

    public Set<E> getChildrenElements() {
        Set<UnsortedMultiBiTreeNode<E>> children = getChildren();
        Set<E> commands = new HashSet<>();
        for (UnsortedMultiBiTreeNode<E> commandTreeNode : children) {
            commands.add(commandTreeNode.getElement());
        }
        return commands;
    }

    public String getElementStringChain() {
        List<String> commandChainList = new ArrayList<>();
        commandChainList.add(this.element.toString());
        UnsortedMultiBiTreeNode<E> node = this;
        while(!node.isRoot()) {
            node = node.getParent();
            commandChainList.add(node.getElement().toString());
        }

        Collections.reverse(commandChainList);
        return Strings.listing(commandChainList, " ");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnsortedMultiBiTreeNode<?> that = (UnsortedMultiBiTreeNode<?>) o;
        return this.uuid.equals(that.getUuid());
    }

    @Override
    public int hashCode() {
        return this.uuid.hashCode();
    }

}
