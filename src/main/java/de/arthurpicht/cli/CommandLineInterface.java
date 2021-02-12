package de.arthurpicht.cli;

import de.arthurpicht.cli.command.CommandParser;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.CommandsHelper;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.exceptions.CommandSpecException;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.CLISpecificationException;
import de.arthurpicht.cli.common.Parser;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionParser;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.ParameterParser;
import de.arthurpicht.cli.parameter.Parameters;

import java.util.ArrayList;
import java.util.List;

public class CommandLineInterface {

    private final CommandLineInterfaceDefinition commandLineInterfaceDefinition;

    public CommandLineInterface(CommandLineInterfaceDefinition commandLineInterfaceDefinition) {

        if (commandLineInterfaceDefinition == null)
            throw new IllegalArgumentException("Parameter 'CommandLineInterfaceDefinition' is null.");

        this.commandLineInterfaceDefinition = commandLineInterfaceDefinition;
    }

    public ParserResult parse(String[] args) throws UnrecognizedArgumentException {

        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        OptionParserResult optionParserResultGlobal = null;
        List<String> commandList = new ArrayList<>();
        Options optionsSpecific = null;
        OptionParserResult optionParserResultSpecific = null;
        Parameters parameters = null;
        List<String> parameterList = new ArrayList<>();
        CommandExecutor commandExecutor = null;

        if (this.commandLineInterfaceDefinition.hasDefinitionOfGlobalOptions()) {
            OptionParser optionParserGlobal = new OptionParser(this.commandLineInterfaceDefinition.getGlobalOptions());
            optionParserGlobal.parse(argumentIterator);
            optionParserResultGlobal = optionParserGlobal.getOptionParserResult();
        }

        if (this.commandLineInterfaceDefinition.hasDefinitionsOfCommands()) {
            CommandParser commandParser = new CommandParser(
                    this.commandLineInterfaceDefinition.getCommandTree(),
                    this.commandLineInterfaceDefinition.getDefaultCommand());
            commandParser.parse(argumentIterator);
            commandList = commandParser.getCommandStringList();
            optionsSpecific = commandParser.getSpecificOptions();
            parameters = commandParser.getParameters();
            commandExecutor = commandParser.getCommandExecutor();
        } else {
            if (this.commandLineInterfaceDefinition.hasDefaultCommand()) {
                DefaultCommand defaultCommand = this.commandLineInterfaceDefinition.getDefaultCommand();
                optionsSpecific = null;
                parameters = defaultCommand.getParameters();
                commandExecutor = defaultCommand.getCommandExecutor();
            }
        }

        if (Options.hasDefinitions(optionsSpecific)) {
            OptionParser optionParserSpecific = new OptionParser(optionsSpecific);
            optionParserSpecific.parse(argumentIterator);
            optionParserResultSpecific = optionParserSpecific.getOptionParserResult();
        }

        if (parameters != null) {
            ParameterParser parameterParser = parameters.getParameterParser();
            parameterParser.parse(argumentIterator);
            parameterList = parameterParser.getParameterList();
        }

        if (argumentIterator.hasNext()) {
            String arg = argumentIterator.getNext();
            throw new UnrecognizedArgumentException(argumentIterator, "Unrecognized argument: " + arg);
        }

        return new ParserResult(args, optionParserResultGlobal, commandList, optionParserResultSpecific, parameterList, commandExecutor);

    }

    /**
     * Parses specified arguments against cli specification and executes CommandExecutor, if found.
     *
     * @param args cli arguments
     * @return parser result
     * @throws UnrecognizedArgumentException
     */
    public ParserResult execute(String[] args) throws UnrecognizedArgumentException, CommandExecutorException {

        if (args == null) throw new IllegalArgumentException("Assertion failed. Method parameter 'args' is null.");

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

    /**
     * Executes CommandExecutor for pre-parsed arguments. No action is performed if no CommandExecutor is bound
     * to parserResult.
     *
     * @param parserResult
     * @throws CommandExecutorException
     */
    public void execute(ParserResult parserResult) throws CommandExecutorException {

        if (parserResult == null) throw new IllegalArgumentException("Assertion failed. Method parameter 'parserResult' is null.");

        CommandExecutor commandExecutor = parserResult.getCommandExecutor();
        if (commandExecutor != null) {
            commandExecutor.execute(
                    parserResult.getOptionParserResultGlobal(),
                    parserResult.getCommandList(),
                    parserResult.getOptionParserResultSpecific(),
                    parserResult.getParameterList()
            );
        }
    }

}
