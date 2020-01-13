package de.arthurpicht.cli.common;

public abstract class Parser {

//    protected boolean isParsed = false;
    protected int lastProcessedIndex;

    public abstract void parse(String[] args, int beginIndex) throws UnrecognizedArgumentException;

    public int getLastProcessedIndex() {
//        if (!isParsed) throw new NotYetParsedException();
        return this.lastProcessedIndex;
    }

}
