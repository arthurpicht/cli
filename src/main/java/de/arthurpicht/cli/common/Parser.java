package de.arthurpicht.cli.common;

public abstract class Parser {

    public abstract void parse(ArgumentIterator argumentIterator) throws UnrecognizedArgumentException;

}
