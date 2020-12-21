package de.arthurpicht.cli.common;

public abstract class Parser {

//    protected int lastProcessedIndex;

    public abstract void parse(ArgumentIterator argumentIterator) throws UnrecognizedArgumentException;

//    public int getLastProcessedIndex() {
//        return this.lastProcessedIndex;
//    }

}
