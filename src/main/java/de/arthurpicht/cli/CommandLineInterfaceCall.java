package de.arthurpicht.cli;

import de.arthurpicht.cli.option.Option;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.*;

public class CommandLineInterfaceCall {

    private final List<String> args;
    private final CommandLineInterfaceDefinition commandLineInterfaceDefinition;
    private final CommandLineInterfaceResult commandLineInterfaceResult;

    public CommandLineInterfaceCall(
            String[] args,
            CommandLineInterfaceDefinition commandLineInterfaceDefinition,
            CommandLineInterfaceResult commandLineInterfaceResult
    ) {
        this.args = List.of(args);
        this.commandLineInterfaceDefinition = commandLineInterfaceDefinition;
        this.commandLineInterfaceResult = commandLineInterfaceResult;
    }

    public List<String> getArgs() {
        return args;
    }

    public CommandLineInterfaceDefinition getCommandLineInterfaceDefinition() {
        return commandLineInterfaceDefinition;
    }

    public CommandLineInterfaceResult getCommandLineInterfaceResult() {
        return commandLineInterfaceResult;
    }

    //    public OptionParserResult getOptionParserResultGlobal() {
//        return this.optionParserResultGlobal != null ? this.optionParserResultGlobal : new OptionParserResult();
//    }
//
    public List<String> getCommandList() {
        return this.commandLineInterfaceResult.getCommandParserResult().getCommandStringList();
//        return this.commandList != null ? this.commandList : new ArrayList<>();
    }

//    public boolean hasSpecificOptions() {
//        return this.optionsSpecific != null && !this.optionsSpecific.isEmpty();
//    }
//
//    public Options getSpecificOptions() {
//        return this.optionsSpecific;
//    }
//
//    public OptionParserResult getOptionParserResultSpecific() {
//        return this.optionParserResultSpecific != null ? this.optionParserResultSpecific : new OptionParserResult();
//    }
//
//    public boolean hasParameters() {
//        return this.parameters != null;
//    }
//
//    public Parameters getParameters() {
//        return this.parameters;
//    }
//
//    public List<String> getParameterList() {
//        return this.parameterList != null ? this.parameterList : new ArrayList<>();
//    }
//
    public CommandExecutor getCommandExecutor() {
        return this.commandLineInterfaceResult.getCommandParserResult().getCommandExecutor();
    }

//    public void debugOut() {
//
//        System.out.println("CLI ParserResult debug output:");
//
//        System.out.println("input args:");
//        String argsString = Strings.listing(this.args, " ", "[", "]");
//        System.out.println("    " + argsString);
//
//        System.out.println("global options:");
//        this.debugOutOptions(this.getOptionParserResultGlobal());
//
//        System.out.println("commands:");
//        String commands = Strings.listing(this.commandList, " ");
//        if (commands.equals("")) {
//            System.out.println("    none");
//        } else {
//            System.out.println("    " + commands);
//        }
//
//        System.out.println("specific Options:");
//        this.debugOutOptions(this.getOptionParserResultSpecific());
//
//        System.out.println("parameters:");
//        String parameters = Strings.listing(this.parameterList, " ", "", "", "\"", "\"");
//        System.out.println("    " + parameters);
//
//    }
//
//    private void debugOutOptions(OptionParserResult optionParserResult) {
//
//        Set<String> optionIdSet = optionParserResult.getIdSet();
//
//        if (optionIdSet.isEmpty()) {
//            System.out.println("    none");
//            return;
//        }
//
//        for (String optionId : optionIdSet) {
//            Option option = optionParserResult.getOption(optionId);
//            String optionString = option.toString() + " Value=";
//            if (option.hasArgument()) {
//                optionString += "'" + optionParserResult.getValue(optionId) + "'";
//            } else {
//                optionString += " none";
//            }
//            System.out.println("    " + optionString);
//        }
//
//    }
}
