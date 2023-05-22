package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.ParserState;

public abstract class OptionParserState extends ParserState {

    protected final Options options;
    protected final OptionParser optionParser;

    public OptionParserState(Options options, OptionParser optionParser) {
        this.options = options;
        this.optionParser = optionParser;
    }

}
