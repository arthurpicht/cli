package de.arthurpicht.cli.common;

import de.arthurpicht.cli.option.OptionParserState;

public abstract class ParserState {

    public abstract OptionParserState process(String[] args, int processIndex) throws UnrecognizedArgumentException;

}
