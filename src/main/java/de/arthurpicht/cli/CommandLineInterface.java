package de.arthurpicht.cli;

import de.arthurpicht.cli.parameter.ParameterParser;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.cli.command.CommandParser;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.CommandsHelper;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.common.CLISpecificationException;
import de.arthurpicht.cli.option.OptionParser;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.ArrayList;
import java.util.List;

public class CommandLineInterface {

    private Options optionsGlobal;
    private Commands commands;
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
     * @param parameters
     */
    public CommandLineInterface(Options optionsGlobal, Commands commands, Parameters parameters) {
        this.optionsGlobal = optionsGlobal;
        this.commands = commands;
        this.parameters = parameters;

        this.optionParserResultGlobal = null;
        this.commandList = new ArrayList<>();
        this.optionParserResultSpecific = null;
        this.parameterList = new ArrayList<>();

        checkPreconditions();
    }

    public ParserResult parse(String[] args) throws UnrecognizedArgumentException {

        int proceedingIndex = -1;

        if (Options.hasDefinitions(this.optionsGlobal)) {
            OptionParser optionParserGlobal = new OptionParser(this.optionsGlobal);
            optionParserGlobal.parse(args, proceedingIndex + 1);
            this.optionParserResultGlobal = optionParserGlobal.getOptionParserResult();
            proceedingIndex = optionParserGlobal.getLastProcessedIndex();
        }

        if (Commands.hasDefinitions(this.commands)) {
            CommandParser commandParser = new CommandParser(this.commands);
            commandParser.parse(args, proceedingIndex + 1);
            this.commandList = commandParser.getCommandStringList();
            proceedingIndex = commandParser.getLastProcessedIndex();
            this.optionsSpecific = commandParser.getSpecificOptions();
        }

        if (Options.hasDefinitions(this.optionsSpecific)) {
            OptionParser optionParserSpecific = new OptionParser(this.optionsSpecific);
            optionParserSpecific.parse(args, proceedingIndex + 1);
            this.optionParserResultSpecific = optionParserSpecific.getOptionParserResult();
            proceedingIndex = optionParserSpecific.getLastProcessedIndex();
        }

        if (this.parameters != null) {
            ParameterParser parameterParser = this.parameters.getParameterParser();
            parameterParser.parse(args, proceedingIndex + 1);
            this.parameterList = parameterParser.getParameterList();
            proceedingIndex = parameterParser.getLastProcessedIndex();
        }

        boolean finished = (proceedingIndex + 1 == args.length);
        if (!finished) throw new UnrecognizedArgumentException(args, proceedingIndex + 1 , "Unrecognized argument: " + args[proceedingIndex + 1]);

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
