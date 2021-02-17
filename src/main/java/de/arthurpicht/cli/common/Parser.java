package de.arthurpicht.cli.common;

import de.arthurpicht.cli.CommandLineInterfaceResultBuilder;

public abstract class Parser {

    protected CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder;

    public Parser(CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder) {
        this.commandLineInterfaceResultBuilder = commandLineInterfaceResultBuilder;
    }

    public abstract void parse(ArgumentIterator argumentIterator) throws UnrecognizedArgumentException, ParsingBrokenEvent;

}
