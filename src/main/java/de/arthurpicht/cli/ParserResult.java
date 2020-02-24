package de.arthurpicht.cli;

import de.arthurpicht.cli.option.Option;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ParserResult {

    private final List<String> args;
    private final OptionParserResult optionParserResultGlobal;
    private final List<String> commandList;
    private final OptionParserResult optionParserResultSpecific;
    private final List<String> parameterList;

    public ParserResult(String[] args, OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific, List<String> parameterList) {
        this.args = Arrays.asList(args);
        this.optionParserResultGlobal = optionParserResultGlobal;
        this.commandList = commandList;
        this.optionParserResultSpecific = optionParserResultSpecific;
        this.parameterList = parameterList;
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

    public List<String> getParameterList() {
        return this.parameterList != null ? this.parameterList : new ArrayList<>();
    }

    public void debugOut() {

        System.out.println("CLI ParserResult debug output:");

        System.out.println("input args:");
        String argsString = Strings.listing(this.args, " ", "[", "]");
        System.out.println("    " + argsString);

        System.out.println("global options:");
        this.debugOutOptions(this.getOptionParserResultGlobal());

        System.out.println("commands:");
        String commands = Strings.listing(this.commandList, " ");
        if (commands.equals("")) {
            System.out.println("    none");
        } else {
            System.out.println("    " + commands);
        }

        System.out.println("specific Options:");
        this.debugOutOptions(this.getOptionParserResultSpecific());

        System.out.println("parameters:");
        String parameters = Strings.listing(this.parameterList, " ", "", "", "\"", "\"");
        System.out.println("    " + parameters);

    }

    private void debugOutOptions(OptionParserResult optionParserResult) {

        Set<String> optionIdSet = optionParserResult.getIdSet();

        if (optionIdSet.isEmpty()) {
            System.out.println("    none");
            return;
        }

        for (String optionId : optionIdSet) {
            Option option = optionParserResult.getOption(optionId);
            String optionString = option.toString() + " Value=";
            if (option.hasArgument()) {
                optionString += "'" + optionParserResult.getValue(optionId) + "'";
            } else {
                optionString += " none";
            }
            System.out.println("    " + optionString);
        }

    }
}
