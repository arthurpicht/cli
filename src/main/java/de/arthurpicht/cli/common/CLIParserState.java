package de.arthurpicht.cli.common;

import de.arthurpicht.cli.option.OptionParserState;

public abstract class CLIParserState {

    public abstract OptionParserState process(String arg) throws UnrecognizedCLArgumentException;

}