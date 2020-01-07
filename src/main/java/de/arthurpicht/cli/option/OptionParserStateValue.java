package de.arthurpicht.cli.option;

public class OptionParserStateValue extends OptionParserState {

    private Option option;
    private String nameArg;

    public OptionParserStateValue(Options options, OptionParser optionParser, Option option, String nameArg) {
        super(options, optionParser);
        this.option = option;
        this.nameArg = nameArg;
    }

    @Override
    public OptionParserState process(String arg) throws OptionParserException {

        if (arg.startsWith("-")) throw new ValueExpectedExcpetion(this.nameArg);

        OptionParserResult optionParserResult = optionParser.getOptionParserResult();
        optionParserResult.addOption(this.option, arg);
//        OptionParserResultBean optionParserResultBean = new OptionParserResultBean(this.option, arg);
//        this.optionParser.addOptionParserResult(optionParserResultBean);

        return new OptionParserStateName(this.options, this.optionParser);
    }

    public String getNameArg() {
        return this.nameArg;
    }
}
