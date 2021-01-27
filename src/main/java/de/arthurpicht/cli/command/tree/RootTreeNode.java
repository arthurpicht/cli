package de.arthurpicht.cli.command.tree;

public class RootTreeNode extends CommandTreeNode {

    private static final String COMMAND_STRING = "@ROOT@";

    public RootTreeNode() {
        super(new OneCommand(COMMAND_STRING));
    }

    @Override
    public boolean isRoot() {
        return true;
    }

    @Override
    public boolean isInitial() {
        return false;
    }

    @Override
    public String getElementStringChain() {
        return COMMAND_STRING;
    }

}
