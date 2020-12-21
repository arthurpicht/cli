package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.ArgumentIterator;

public class OptionParserStateValue extends OptionParserState {

    private final Option option;

    public OptionParserStateValue(Options options, OptionParser optionParser, Option option) {
        super(options, optionParser);
        this.option = option;
    }

    @Override
    public OptionParserState process(ArgumentIterator argumentIterator) throws OptionParserException {

        String arg = argumentIterator.getCurrent();

        if (arg.startsWith("-")) {
            throw ValueExpectedException.forPreviousArgument(argumentIterator);
        }

        OptionParserResult optionParserResult = optionParser.getOptionParserResult();
        optionParserResult.addOption(this.option, arg);

        return new OptionParserStateName(this.options, this.optionParser);
    }

}
