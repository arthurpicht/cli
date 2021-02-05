package de.arthurpicht.cli;

import de.arthurpicht.cli.command.CommandParser;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.CommandsHelper;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.exceptions.CommandSpecException;
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
    private CommandExecutor commandExecutor;

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

        if (commands == null) throw new CLISpecificationException("No commands specified. Specify default command at least.");

        this.optionsGlobal = optionsGlobal;
        this.commands = commands;

        this.optionParserResultGlobal = null;
        this.commandList = new ArrayList<>();
        this.optionParserResultSpecific = null;
        this.parameterList = new ArrayList<>();
        this.commandExecutor = null;

        checkPreconditions();
    }

    public ParserResult parse(String[] args) throws UnrecognizedArgumentException {

        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        if (Options.hasDefinitions(this.optionsGlobal)) {
            OptionParser optionParserGlobal = new OptionParser(this.optionsGlobal);
            optionParserGlobal.parse(argumentIterator);
            this.optionParserResultGlobal = optionParserGlobal.getOptionParserResult();
        }

        if (Commands.hasDefinitions(this.commands)) {
            CommandParser commandParser = new CommandParser(this.commands);
            commandParser.parse(argumentIterator);
            this.commandList = commandParser.getCommandStringList();
            this.optionsSpecific = commandParser.getSpecificOptions();
            this.parameters = commandParser.getParameters();
            this.commandExecutor = commandParser.getCommandExecutor();
        } else {
            if (Commands.hasDefaultCommand(this.commands)) {
                DefaultCommand defaultCommand = this.commands.getDefaultCommand();
                this.optionsSpecific = null;
                this.parameters = defaultCommand.getParameters();
                this.commandExecutor = defaultCommand.getCommandExecutor();
            }
        }

        if (Options.hasDefinitions(this.optionsSpecific)) {
            OptionParser optionParserSpecific = new OptionParser(this.optionsSpecific);
            optionParserSpecific.parse(argumentIterator);
            this.optionParserResultSpecific = optionParserSpecific.getOptionParserResult();
        }

        if (this.parameters != null) {
            ParameterParser parameterParser = this.parameters.getParameterParser();
            parameterParser.parse(argumentIterator);
            this.parameterList = parameterParser.getParameterList();
        }

        if (argumentIterator.hasNext()) {
            String arg = argumentIterator.getNext();
            throw new UnrecognizedArgumentException(argumentIterator, "Unrecognized argument: " + arg);
        }

        return new ParserResult(args, this.optionParserResultGlobal, this.commandList, this.optionParserResultSpecific, this.parameterList, this.commandExecutor);

    }

    /**
     * Parses specified arguments against cli specification and executes CommandExecutor, if found.
     *
     * @param args cli arguments
     * @return parser result
     * @throws UnrecognizedArgumentException
     */
    public ParserResult execute(String[] args) throws UnrecognizedArgumentException {
        ParserResult parserResult = this.parse(args);
        CommandExecutor commandExecutor = parserResult.getCommandExecutor();
        if (commandExecutor != null) {
            commandExecutor.execute(
                    parserResult.getOptionParserResultGlobal(),
                    parserResult.getCommandList(),
                    parserResult.getOptionParserResultSpecific(),
                    parserResult.getParameterList()
            );
        }
        return parserResult;
    }

    private void checkPreconditions() {

        if (this.commands.isEmpty() && !this.commands.hasDefaultCommand())
            throw new CLISpecificationException("Commands are specified as empty. Specify at least default command.");

        if (this.optionsGlobal != null && !this.optionsGlobal.isEmpty() && this.optionsSpecific != null && !this.optionsSpecific.isEmpty()) {
            if (this.commands.isEmpty()) {
                throw new CLISpecificationException("Commands must be specified if both global options and specific options are specified.");
            }
        }

//        if (this.parameters != null && commands != null && CommandsHelper.hasOpenLeaves(this.commands)) {
//            throw new CLISpecificationException("Commands must not end open if parameters are defined.");
//        }
    }

}
