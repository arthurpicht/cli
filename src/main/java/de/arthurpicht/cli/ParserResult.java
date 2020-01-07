package de.arthurpicht.cli;

import de.arthurpicht.cli.option.OptionParserResult;

import java.util.List;

public class ParserResult {

    private final OptionParserResult optionParserResultGlobal;
    private final List<String> commandList;
    private final OptionParserResult optionParserResultSpecific;
    private final List<String> argumentList;

    public ParserResult(OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific, List<String> argumentList) {
        this.optionParserResultGlobal = optionParserResultGlobal;
        this.commandList = commandList;
        this.optionParserResultSpecific = optionParserResultSpecific;
        this.argumentList = argumentList;
    }

    public OptionParserResult getOptionParserResultGlobal() {
        return this.optionParserResultGlobal;
    }

    public List<String> getCommandList() {
        return this.commandList;
    }

    public OptionParserResult getOptionParserResultSpecific() {
        return this.optionParserResultSpecific;
    }

    public List<String> getArgumentList() {
        return argumentList;
    }
}
