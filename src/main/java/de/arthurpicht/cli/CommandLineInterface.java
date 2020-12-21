package de.arthurpicht.cli;

import de.arthurpicht.cli.command.CommandParser;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.CommandsHelper;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.CLISpecificationException;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionParser;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.ParameterParser;
import de.arthurpicht.cli.parameter.Parameters;

import java.util.ArrayList;
import java.util.List;

public class CommandLineInterface {

    private final Options optionsGlobal;
    private final Commands commands;
    private Options optionsSpecific;
    private Parameters parameters;

    private OptionParserResult optionParserResultGlobal;
    private List<String> commandList;
    private OptionParserResult optionParserResultSpecific;
    private List<String> parameterList;

    /**
     * Initialisierung der CLI-Spezifikation. Es bestehen folgende Einschränkungen:
     * <ul>
     *     <li>Wenn sowohl globale als auch spezifische Optionen spezifiziert sind müssen auch Commands definiert sein.</li>
     *     <li>Wenn Parameter definiert sind, dann dürfen die Command-Spezifikationen nicht mit einem offenen Command enden.</li>
     * </ul>
     *
     * @param optionsGlobal
     * @param commands
     */
    public CommandLineInterface(Options optionsGlobal, Commands commands) {
        this.optionsGlobal = optionsGlobal;
        this.commands = commands;

        this.optionParserResultGlobal = null;
        this.commandList = new ArrayList<>();
        this.optionParserResultSpecific = null;
        this.parameterList = new ArrayList<>();

        checkPreconditions();
    }

    public ParserResult parse(String[] args) throws UnrecognizedArgumentException {

        ArgumentIterator argumentIterator = new ArgumentIterator(args);
//        int proceedingIndex = -1;

        if (Options.hasDefinitions(this.optionsGlobal)) {
            OptionParser optionParserGlobal = new OptionParser(this.optionsGlobal);
            optionParserGlobal.parse(argumentIterator);
            this.optionParserResultGlobal = optionParserGlobal.getOptionParserResult();
//            proceedingIndex = optionParserGlobal.getLastProcessedIndex();
        }

        if (Commands.hasDefinitions(this.commands)) {
            CommandParser commandParser = new CommandParser(this.commands);
            commandParser.parse(argumentIterator);
            this.commandList = commandParser.getCommandStringList();
//            proceedingIndex = commandParser.getLastProcessedIndex();
            this.optionsSpecific = commandParser.getSpecificOptions();
            this.parameters = commandParser.getParameters();
        }

        if (Options.hasDefinitions(this.optionsSpecific)) {
            OptionParser optionParserSpecific = new OptionParser(this.optionsSpecific);
            optionParserSpecific.parse(argumentIterator);
            this.optionParserResultSpecific = optionParserSpecific.getOptionParserResult();
//            proceedingIndex = optionParserSpecific.getLastProcessedIndex();
        }

        if (this.parameters != null) {
            ParameterParser parameterParser = this.parameters.getParameterParser();
            parameterParser.parse(argumentIterator);
            this.parameterList = parameterParser.getParameterList();
//            proceedingIndex = parameterParser.getLastProcessedIndex();
        }

//        boolean finished = (proceedingIndex + 1 == args.length);
//        boolean finished = !argumentIterator.hasNext();
//        if (!finished) throw new UnrecognizedArgumentException(argumentIterator.getArguments(), argumentIterator.getIndex() + 1 , "Unrecognized argument: " + args[proceedingIndex + 1]);

        if (argumentIterator.hasNext()) {
            String arg = argumentIterator.getNext();
            throw new UnrecognizedArgumentException(argumentIterator, "Unrecognized argument: " + arg);
        }

        return new ParserResult(args, this.optionParserResultGlobal, this.commandList, this.optionParserResultSpecific, this.parameterList);

    }

    private void checkPreconditions() {

        if (this.optionsGlobal != null && !this.optionsGlobal.isEmpty() && this.optionsSpecific != null && !this.optionsSpecific.isEmpty()) {
            if (this.commands == null || this.commands.isEmpty()) {
                throw new CLISpecificationException("Commands must be specified if global options and specific options are specified.");
            }
        }

        if (this.parameters != null && commands != null && CommandsHelper.hasOpenLeaves(this.commands)) {
            throw new CLISpecificationException("Commands must not end open if parameters are defined.");
        }
    }

}
