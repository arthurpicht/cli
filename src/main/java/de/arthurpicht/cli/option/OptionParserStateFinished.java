package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.ArgumentIterator;

public class OptionParserStateFinished extends OptionParserState {

    public OptionParserStateFinished(Options options, OptionParser optionParser) {
        super(options, optionParser);
    }

    @Override
    public OptionParserState process(ArgumentIterator argumentIterator) {
        return null;
    }
}
