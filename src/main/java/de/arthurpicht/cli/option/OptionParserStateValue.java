package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.ArgumentIterator;

public class OptionParserStateValue extends OptionParserState {

    private final Option option;
    private final String executableName;

    public OptionParserStateValue(Options options, OptionParser optionParser, Option option, String executableName) {
        super(options, optionParser);
        this.option = option;
        this.executableName = executableName;
    }

    @Override
    public OptionParserState process(ArgumentIterator argumentIterator) throws OptionParserException {

        String arg = argumentIterator.getCurrent();

        if (arg.startsWith("-")) {
            throw ValueExpectedException.forPreviousArgument(executableName, argumentIterator);
        }

        OptionParserResult optionParserResult = optionParser.getParserResult();
        optionParserResult.addOption(this.option, arg);

        return new OptionParserStateName(this.options, this.optionParser, this.executableName);
    }

}
