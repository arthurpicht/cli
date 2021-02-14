package de.arthurpicht.cli;

import de.arthurpicht.cli.command.CommandParser;
import de.arthurpicht.cli.command.CommandParserResult;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionParser;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.ParameterParser;
import de.arthurpicht.cli.parameter.ParameterParserResult;
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

    public CommandLineInterfaceCall parse(String[] args) throws UnrecognizedArgumentException {

        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        OptionParserResult optionParserResultGlobal = new OptionParserResult();
        CommandParserResult commandParserResult = new CommandParserResult();
        OptionParserResult optionParserResultSpecific = new OptionParserResult();
        ParameterParserResult parameterParserResult = new ParameterParserResult();

        if (this.commandLineInterfaceDefinition.hasGlobalOptions()) {
            OptionParser optionParserGlobal = new OptionParser(this.commandLineInterfaceDefinition.getGlobalOptions());
            optionParserGlobal.parse(argumentIterator);
            optionParserResultGlobal = optionParserGlobal.getParserResult();
        }

        if (this.commandLineInterfaceDefinition.hasCommands()) {
            CommandParser commandParser = new CommandParser(
                    this.commandLineInterfaceDefinition.getCommandTree(),
                    this.commandLineInterfaceDefinition.getDefaultCommand());
            commandParser.parse(argumentIterator);
            commandParserResult = commandParser.getParserResult();
        } else {
            if (this.commandLineInterfaceDefinition.hasDefaultCommand()) {
                DefaultCommand defaultCommand = this.commandLineInterfaceDefinition.getDefaultCommand();
                commandParserResult = new CommandParserResult(
                        new ArrayList<>(),
                        null,
                        defaultCommand.getParameters(),
                        defaultCommand.getCommandExecutor()
                );
            }
        }

        if (commandParserResult.hasSpecificOptions()) {
            Options optionsSpecific = commandParserResult.getSpecificOptions();
            OptionParser optionParserSpecific = new OptionParser(optionsSpecific);
            optionParserSpecific.parse(argumentIterator);
            optionParserResultSpecific = optionParserSpecific.getParserResult();
        }

        if (commandParserResult.hasParameters()) {
            Parameters parameters = commandParserResult.getParameters();
            ParameterParser parameterParser = parameters.getParameterParser();
            parameterParser.parse(argumentIterator);
            parameterParserResult = parameterParser.getParserResult();
        }

        if (argumentIterator.hasNext()) {
            String arg = argumentIterator.getNext();
            throw new UnrecognizedArgumentException(argumentIterator, "Unrecognized argument: " + arg);
        }

        CommandLineInterfaceResult commandLineInterfaceResult = new CommandLineInterfaceResult(
                optionParserResultGlobal,
                commandParserResult,
                optionParserResultSpecific,
                parameterParserResult,
                commandParserResult.getCommandExecutor()
        );

        return new CommandLineInterfaceCall(
                args,
                this.commandLineInterfaceDefinition,
                commandLineInterfaceResult
        );
    }

    /**
     * Parses specified arguments against cli specification and executes CommandExecutor, if found.
     *
     * @param args cli arguments
     * @return parser result
     * @throws UnrecognizedArgumentException
     */
    public CommandLineInterfaceCall execute(String[] args) throws UnrecognizedArgumentException, CommandExecutorException {

        if (args == null) throw new IllegalArgumentException("Assertion failed. Method parameter 'args' is null.");

        CommandLineInterfaceCall commandLineInterfaceCall = this.parse(args);
        CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();
        CommandExecutor commandExecutor = commandLineInterfaceResult.getCommandParserResult().getCommandExecutor();
        if (commandExecutor != null) {
            commandExecutor.execute(
                    commandLineInterfaceResult.getOptionParserResultGlobal(),
                    commandLineInterfaceResult.getCommandParserResult().getCommandStringList(),
                    commandLineInterfaceResult.getOptionParserResultSpecific(),
                    commandLineInterfaceResult.getParameterParserResult()
            );
        }
        return commandLineInterfaceCall;
    }

    /**
     * Executes CommandExecutor for pre-parsed arguments. No action is performed if no CommandExecutor is bound
     * to parserResult.
     *
     * @param commandLineInterfaceCall
     * @throws CommandExecutorException
     */
    public void execute(CommandLineInterfaceCall commandLineInterfaceCall) throws CommandExecutorException {

        if (commandLineInterfaceCall == null) throw new IllegalArgumentException("Assertion failed. Method parameter 'parserResult' is null.");

        CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();
        CommandExecutor commandExecutor = commandLineInterfaceResult.getCommandParserResult().getCommandExecutor();

        if (commandExecutor != null) {
            commandExecutor.execute(
                    commandLineInterfaceResult.getOptionParserResultGlobal(),
                    commandLineInterfaceResult.getCommandParserResult().getCommandStringList(),
                    commandLineInterfaceResult.getOptionParserResultSpecific(),
                    commandLineInterfaceResult.getParameterParserResult()
            );
        }
    }

}
