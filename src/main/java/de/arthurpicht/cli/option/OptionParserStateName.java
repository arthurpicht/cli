package de.arthurpicht.cli.option;

public class OptionParserStateName extends OptionParserState {

    public OptionParserStateName(Options options, OptionParser optionParser) {
        super(options, optionParser);
    }

    @Override
    public OptionParserState process(String arg) throws OptionParserException {

        Option option;

        if (arg.startsWith("--")) {
            String longName = arg.substring(2);
            if (longName.equals("")) throw new IllegalArgException(arg);

            if (!this.options.hasLongNameOption(longName)) throw new UnknownArgumentException(arg);
            option = this.options.getLongNameOption(longName);

        } else if (arg.startsWith("-")) {
            String shortName = arg.substring(1);
            if (shortName.equals("")) throw new IllegalArgException(arg);
            if (shortName.length() > 1) throw new IllegalArgException(arg);
            Character shortNameChar = shortName.charAt(0);

            if (!this.options.hasShortNameOption(shortNameChar)) throw new UnknownArgumentException(arg);
            option = this.options.getShortNameOption(shortNameChar);

        } else {
            return new OptionParserStateFinished(this.options, this.optionParser);
//            throw new IllegalArgException(arg);
        }

        if (option.hasArgument()) {
            return new OptionParserStateValue(this.options, this.optionParser, option, arg);
        }

        OptionParserResult optionParserResult = this.optionParser.getOptionParserResult();
        optionParserResult.addOption(option);

//        OptionParserResultBean optionParserResultBean = new OptionParserResultBean(option);
//        this.optionParser.addOptionParserResult(optionParserResultBean);

        return new OptionParserStateName(this.options, this.optionParser);

    }


}
