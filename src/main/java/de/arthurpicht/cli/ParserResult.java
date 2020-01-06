package de.arthurpicht.cli;

import de.arthurpicht.cli.option.OptionParserResult;

public class ParserResult {

    private final OptionParserResult optionParserResultGlobal;
    private final OptionParserResult optionParserResultSpecific;

    public ParserResult(OptionParserResult optionParserResultGlobal, OptionParserResult optionParserResultSpecific) {
        this.optionParserResultGlobal = optionParserResultGlobal;
        this.optionParserResultSpecific = optionParserResultSpecific;
    }

    public OptionParserResult getOptionParserResultGlobal() {
        return this.optionParserResultGlobal;
    }

    public OptionParserResult getOptionParserResultSpecific() {
        return this.optionParserResultSpecific;
    }
}
