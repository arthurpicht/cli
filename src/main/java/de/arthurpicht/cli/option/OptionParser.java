package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Parser;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;

public class OptionParser extends Parser {

    private final Options options;
    private final OptionParserResult optionParserResult;

    public OptionParser(Options options) {
        this.options = options;
        this.optionParserResult = new OptionParserResult();
    }

    public OptionParserResult getOptionParserResult() {
        return this.optionParserResult;
    }

    @Override
    public void parse(ArgumentIterator argumentIterator) throws UnrecognizedArgumentException {

        if (!argumentIterator.hasNext()) return;

        OptionParserState optionParserState = new OptionParserStateName(options, this);
        while (argumentIterator.hasNext()) {

            argumentIterator.getNext();
            optionParserState = optionParserState.process(argumentIterator);

            if (optionParserState instanceof OptionParserStateFinished) {
                stepBack(argumentIterator);
                return;
            }
        }

        if (optionParserState instanceof OptionParserStateValue) {
            throw new ValueExpectedException(argumentIterator);
        }

    }

    private void stepBack(ArgumentIterator argumentIterator) {
        if (argumentIterator.hasPrevious()) {
            argumentIterator.getPrevious();
        } else {
            argumentIterator.reset();
        }
    }

}
