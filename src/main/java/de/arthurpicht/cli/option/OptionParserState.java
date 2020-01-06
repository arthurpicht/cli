package de.arthurpicht.cli.option;

import de.arthurpicht.cli.Options;
import de.arthurpicht.cli.common.CLIParserState;

public abstract class OptionParserState extends CLIParserState {

    protected Options options;
    protected OptionParser optionParser;

    public OptionParserState(Options options, OptionParser optionParser) {
        this.options = options;
        this.optionParser = optionParser;
    }

//    public abstract OptionParserState process(String arg) throws OptionParserException;

}
