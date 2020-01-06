package de.arthurpicht.cli;

import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.common.CLIParserException;
import de.arthurpicht.cli.option.Option;
import de.arthurpicht.cli.option.OptionParser;
import de.arthurpicht.cli.option.OptionParserException;
import de.arthurpicht.cli.option.OptionParserResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandLineInterface {

    private Options globalOptions;
    private Commands commands;
    private Options specificOptions;

    public CommandLineInterface(Options globalOptions, Commands commands, Options specificOptions) {
        this.globalOptions = globalOptions;
        this.commands = commands;
        this.specificOptions = specificOptions;
    }

    public ParserResult parse(String[] args) throws CLIParserException {

//        OptionParserResult optionParserResultGlobal = null;
//        List<String> argsList = Arrays.asList(args);
//
//        if (this.globalOptions != null) {
//            OptionParser optionParserGlobal = new OptionParser(this.globalOptions, argsList.toArray(new String[0]));
//            optionParserResultGlobal = optionParserGlobal.getOptionParserResult();
//            argsList = argsList.subList(optionParserGlobal.getLastProcessedArgIndex(), argsList.size());
//        }
//
//        int indexFirstOption = getIndexOfFirstOption(argsList);
//        List<String> argsListCommands;
//        List<String> argsListOptions;
//        if (indexFirstOption >= 0) {
//            argsListCommands = argsList.subList(0, indexFirstOption);
//            argsListOptions = argsList.subList(indexFirstOption + 1, argsList.size());
//        } else {
//            argsListCommands = argsList;
//            argsListOptions = new ArrayList<>();
//        }
//



        throw new RuntimeException("NIY");
    }

//    private List<String> getList

    private int getIndexOfFirstOption(List<String> argList) {
        for (int i = 0; i < argList.size(); i++) {
            if (argList.get(0).startsWith("-")) {
                return i;
            }
        }
        return -1;
    }


}
