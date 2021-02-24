package de.arthurpicht.cli.common;

import de.arthurpicht.cli.CliResultBuilder;

public abstract class Parser {

    protected CliResultBuilder cliResultBuilder;

    public Parser(CliResultBuilder cliResultBuilder) {
        this.cliResultBuilder = cliResultBuilder;
    }

    public abstract void parse(ArgumentIterator argumentIterator) throws UnrecognizedArgumentException, ParsingBrokenEvent;

}
