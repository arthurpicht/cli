package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.ArgumentIterator;

public class OptionParserStateName extends OptionParserState {

    public OptionParserStateName(Options options, OptionParser optionParser) {
        super(options, optionParser);
    }

    @Override
    public OptionParserState process(ArgumentIterator argumentIterator) throws OptionParserException {

        String arg = argumentIterator.getCurrent();
        Option option;

        if (arg.startsWith("--")) {
            String longName = arg.substring(2);
            if (longName.equals("")) throw new MalformedOptionException(argumentIterator);

            if (!this.options.hasLongNameOption(longName)) throw new UnspecifiedOptionException(argumentIterator);
            option = this.options.getLongNameOption(longName);

        } else if (arg.startsWith("-")) {
            String shortName = arg.substring(1);
            if (shortName.equals("")) throw new MalformedOptionException(argumentIterator);
            if (shortName.length() > 1) throw new MalformedOptionException(argumentIterator);
            Character shortNameChar = shortName.charAt(0);

            if (!this.options.hasShortNameOption(shortNameChar)) throw new UnspecifiedOptionException(argumentIterator);
            option = this.options.getShortNameOption(shortNameChar);

        } else {
            return new OptionParserStateFinished(this.options, this.optionParser);
        }

        if (option.hasArgument()) {
            return new OptionParserStateValue(this.options, this.optionParser, option);
        }

        OptionParserResult optionParserResult = this.optionParser.getParserResult();
        optionParserResult.addOption(option);

        return new OptionParserStateName(this.options, this.optionParser);
    }

}
