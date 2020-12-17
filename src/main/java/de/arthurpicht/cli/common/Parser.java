package de.arthurpicht.cli.common;

public abstract class Parser {

    protected int lastProcessedIndex;

    public abstract void parse(String[] args, int beginIndex) throws UnrecognizedArgumentException;

    public int getLastProcessedIndex() {
        return this.lastProcessedIndex;
    }

}
