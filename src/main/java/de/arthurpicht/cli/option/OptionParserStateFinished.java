package de.arthurpicht.cli.option;

import de.arthurpicht.cli.Options;

public class OptionParserStateFinished extends OptionParserState {

    public OptionParserStateFinished(Options options, OptionParser optionParser) {
        super(options, optionParser);
    }

    @Override
    public OptionParserState process(String arg) throws OptionParserException {
        return null;
    }
}
