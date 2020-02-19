package de.arthurpicht.cli;

import de.arthurpicht.cli.option.OptionParserResult;

import java.util.ArrayList;
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
        return this.optionParserResultGlobal != null ? this.optionParserResultGlobal : new OptionParserResult();
    }

    public List<String> getCommandList() {
        return this.commandList != null ? this.commandList : new ArrayList<>();
    }

    public OptionParserResult getOptionParserResultSpecific() {
        return this.optionParserResultSpecific != null ? this.optionParserResultSpecific : new OptionParserResult();
    }

    public List<String> getArgumentList() {
        return this.argumentList != null ? this.argumentList : new ArrayList<>();
    }
}
