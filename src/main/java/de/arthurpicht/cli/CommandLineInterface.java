package de.arthurpicht.cli;

import de.arthurpicht.cli.argument.ArgumentParser;
import de.arthurpicht.cli.argument.Arguments;
import de.arthurpicht.cli.command.CommandParser;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.CommandsHelper;
import de.arthurpicht.cli.common.UnrecognizedCLArgumentException;
import de.arthurpicht.cli.common.CLISpecificationException;
import de.arthurpicht.cli.option.OptionParser;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.utils.core.collection.Lists;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.ArrayList;
import java.util.List;

public class CommandLineInterface {

    private Options optionsGlobal;
    private Commands commands;
    private Options optionsSpecific;
    private Arguments arguments;

    private OptionParserResult optionParserResultGlobal;
    private List<String> commandList;
    private OptionParserResult optionParserResultSpecific;
    private List<String> argumentList;

    /**
     * Initialisierung der CLI-Spezifikation. Es bestehen folgende Einschränkungen:
     * <ul>
     *     <li>Wenn sowohl globale als auch spezifische Optionen spezifiziert sind müssen auch Commands definiert sein.</li>
     *     <li>Wenn Argumente definiert sind, dann dürfen die Command-Spezifikationen nicht mit einem offenen Command enden.</li>
     * </ul>
     *
     * @param optionsGlobal
     * @param commands
     * @param optionsSpecific
     * @param arguments
     */
    public CommandLineInterface(Options optionsGlobal, Commands commands, Options optionsSpecific, Arguments arguments) {
        this.optionsGlobal = optionsGlobal;
        this.commands = commands;
        this.optionsSpecific = optionsSpecific;
        this.arguments = arguments;

        this.optionParserResultGlobal = null;
        this.commandList = new ArrayList<>();
        this.optionParserResultSpecific = null;
        this.argumentList = new ArrayList<>();

        checkPreconditions();
    }

    public ParserResult parse(String[] args) throws UnrecognizedCLArgumentException {

        int proceedingIndex = -1;

        if (this.optionsGlobal != null) {
            OptionParser optionParserGlobal = new OptionParser(this.optionsGlobal);
            optionParserGlobal.parse(args, proceedingIndex + 1);
            this.optionParserResultGlobal = optionParserGlobal.getOptionParserResult();
            proceedingIndex = optionParserGlobal.getLastProcessedIndex();
        }

        if (this.commands != null) {
            CommandParser commandParser = new CommandParser(this.commands);
            commandParser.parse(args, proceedingIndex + 1);
            this.commandList = commandParser.getCommandStringList();
            proceedingIndex = commandParser.getLastProcessedIndex();
        }

        if (this.optionsSpecific != null) {
            OptionParser optionParserSpecific = new OptionParser(this.optionsSpecific);
            optionParserSpecific.parse(args, proceedingIndex + 1);
            this.optionParserResultSpecific = optionParserSpecific.getOptionParserResult();
            proceedingIndex = optionParserSpecific.getLastProcessedIndex();
        }

        if (this.arguments != null) {
            ArgumentParser argumentParser = this.arguments.getArgumentParser();
            argumentParser.parse(args, proceedingIndex + 1);
            this.argumentList = argumentParser.getArgumentList();
            proceedingIndex = argumentParser.getLastProcessedIndex();
        }

        boolean finished = (proceedingIndex + 1 == args.length);
        if (!finished) throw new UnrecognizedCLArgumentException("Unrecognized argument: " + args[proceedingIndex + 1]);

        return new ParserResult(this.optionParserResultGlobal, this.commandList, this.optionParserResultSpecific, this.argumentList);

    }

    private void checkPreconditions() {

        if (this.optionsGlobal != null && !this.optionsGlobal.isEmpty() && this.optionsSpecific != null && !this.optionsSpecific.isEmpty()) {
            if (this.commands == null || this.commands.isEmpty()) {
                throw new CLISpecificationException("Commands must be specified if global options and specific options are specified.");
            }
        }

        if (this.arguments != null && commands != null && CommandsHelper.hasOpenLeaves(this.commands)) {
            throw new CLISpecificationException("Commands must not end open if arguments are defined.");
        }
    }

//    public static String getArgsString(String[] args) {
//        return Strings.listing(Lists.newArrayList(args), "");
//    }


//    private List<CLIAbstractParser> getParserList() {
//        List<CLIAbstractParser> parserList = new ArrayList<>();
//
//        if (this.optionsGlobal != null) {
//            parserList.add(new OptionParser(this.optionsGlobal));
//        }
//
//        // TODO CommandParser
//
//        if (this.optionsSpecific != null) {
//            parserList.add(new OptionParser(this.optionsSpecific));
//        }
//
//        if (this.arguments != null) {
//            parserList.add(arguments.getArgumentParser());
//        }
//
//        return parserList;
//    }
//
//
//    private int getIndexOfFirstOption(List<String> argList) {
//        for (int i = 0; i < argList.size(); i++) {
//            if (argList.get(0).startsWith("-")) {
//                return i;
//            }
//        }
//        return -1;
//    }


}
