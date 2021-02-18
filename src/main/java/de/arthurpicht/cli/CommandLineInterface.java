package de.arthurpicht.cli;

import de.arthurpicht.cli.CommandLineInterfaceResult.Status;
import de.arthurpicht.cli.command.CommandParser;
import de.arthurpicht.cli.command.CommandParserResult;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.exceptions.AmbiguousCommandException;
import de.arthurpicht.cli.command.exceptions.IllegalCommandException;
import de.arthurpicht.cli.command.exceptions.InsufficientNrOfCommandsException;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.GenericCommandExecutor;
import de.arthurpicht.cli.common.ParsingBrokenEvent;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionParser;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.ParameterParser;
import de.arthurpicht.cli.parameter.Parameters;

import java.util.ArrayList;

import static de.arthurpicht.cli.CommandLineInterfaceResult.Status.COMPLETE;

public class CommandLineInterface {

    private final CommandLineInterfaceDefinition commandLineInterfaceDefinition;
    private final CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder;
    private boolean calledBefore;

    /**
     * Initializes CommandLIneInterface. Usage for end user is discouraged. Use {@link CommandLineInterfaceBuilder}
     * instead.
     *
     * @param commandLineInterfaceDefinition
     */
    public CommandLineInterface(CommandLineInterfaceDefinition commandLineInterfaceDefinition) {

        if (commandLineInterfaceDefinition == null)
            throw new IllegalArgumentException("Parameter 'CommandLineInterfaceDefinition' is null.");

        this.commandLineInterfaceDefinition = commandLineInterfaceDefinition;
        this.commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
        this.calledBefore = false;
    }

    /**
     * Parses arguments without executing command specific {@link CommandExecutor}.
     *
     * @param args arguments as given by end user on cli.
     * @return
     * @throws UnrecognizedArgumentException
     * @throws IllegalStateException if called more than once
     */
    public CommandLineInterfaceCall parse(String[] args) throws UnrecognizedArgumentException {

        assertNotCalledBefore();

        CommandLineInterfaceResult commandLineInterfaceResult;

        try {
            commandLineInterfaceResult = parseCore(args);
        } catch (ParsingBrokenEvent parsingBrokenEvent) {
            commandLineInterfaceResult = parsingBrokenEvent.getCommandLineInterfaceResult();
        }

        return new CommandLineInterfaceCall(
                args,
                this.commandLineInterfaceDefinition,
                commandLineInterfaceResult
        );
    }

    private CommandLineInterfaceResult parseCore(String[] args) throws UnrecognizedArgumentException, ParsingBrokenEvent {

        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        parseGlobalOptions(argumentIterator);

        parseCommands(argumentIterator);

        parseSpecificOptions(argumentIterator);

        parseParameters(argumentIterator);

        handleSurplusArguments(argumentIterator);

        return commandLineInterfaceResultBuilder.build(COMPLETE);
    }

    private void parseGlobalOptions(ArgumentIterator argumentIterator) throws ParsingBrokenEvent, UnrecognizedArgumentException {
        if (this.commandLineInterfaceDefinition.hasGlobalOptions()) {
            OptionParser optionParserGlobal = new OptionParser(
                    OptionParser.Target.GLOBAL,
                    this.commandLineInterfaceDefinition.getGlobalOptions(),
                    this.commandLineInterfaceResultBuilder);
            optionParserGlobal.parse(argumentIterator);
        }
    }

    private void parseCommands(ArgumentIterator argumentIterator) throws InsufficientNrOfCommandsException, IllegalCommandException, AmbiguousCommandException {

        if (this.commandLineInterfaceDefinition.hasCommands()) {
            CommandParser commandParser = new CommandParser(
                    this.commandLineInterfaceDefinition.getCommandTree(),
                    this.commandLineInterfaceDefinition.getDefaultCommand(),
                    this.commandLineInterfaceResultBuilder);
            commandParser.parse(argumentIterator);
        } else {
            if (this.commandLineInterfaceDefinition.hasDefaultCommand()) {
                DefaultCommand defaultCommand = this.commandLineInterfaceDefinition.getDefaultCommand();
                this.commandLineInterfaceResultBuilder.withCommandParserResult(
                        new CommandParserResult(
                                new ArrayList<>(),
                                null,
                                defaultCommand.getParameters(),
                                defaultCommand.getCommandExecutor(),
                                defaultCommand.getDescription()
                        ));
            }
        }
    }

    private void parseSpecificOptions(ArgumentIterator argumentIterator) throws ParsingBrokenEvent, UnrecognizedArgumentException {
        if (this.commandLineInterfaceResultBuilder.hasSpecificOptions()) {
            Options optionsSpecific = this.commandLineInterfaceResultBuilder.getSpecificOptions();
            OptionParser optionParserSpecific = new OptionParser(
                    OptionParser.Target.SPECIFIC,
                    optionsSpecific,
                    this.commandLineInterfaceResultBuilder);
            optionParserSpecific.parse(argumentIterator);
        }
    }

    private void parseParameters(ArgumentIterator argumentIterator) throws ParsingBrokenEvent, UnrecognizedArgumentException {
        if (this.commandLineInterfaceResultBuilder.hasParameters()) {
            Parameters parameters = this.commandLineInterfaceResultBuilder.getParameters();
            ParameterParser parameterParser = parameters.getParameterParser(this.commandLineInterfaceResultBuilder);
            parameterParser.parse(argumentIterator);
        }
    }

    private void handleSurplusArguments(ArgumentIterator argumentIterator) throws UnrecognizedArgumentException {
        if (argumentIterator.hasNext()) {
            String arg = argumentIterator.getNext();
            throw new UnrecognizedArgumentException(argumentIterator, "Unrecognized argument: " + arg);
        }
    }

    /**
     * Parses specified arguments against cli specification and executes CommandExecutor, if found.
     *
     * @param args cli arguments
     * @return CommandLineInterfaceCall
     * @throws UnrecognizedArgumentException if an argument can not be recognized as an syntactical element of the cli
     */
    public CommandLineInterfaceCall execute(String[] args) throws UnrecognizedArgumentException, CommandExecutorException {

        if (args == null) throw new IllegalArgumentException("Assertion failed. Method parameter 'args' is null.");

        CommandLineInterfaceCall commandLineInterfaceCall = this.parse(args);
        if (commandLineInterfaceCall.getStatus() == Status.BROKEN) {
            GenericCommandExecutor.apply(commandLineInterfaceCall);
        } else if (commandLineInterfaceCall.hasCommandExecutor()) {
            CommandExecutor commandExecutor = commandLineInterfaceCall.getCommandExecutor();
            commandExecutor.execute(commandLineInterfaceCall);
        }
        return commandLineInterfaceCall;
    }

    /**
     * Executes CommandExecutor for pre-parsed arguments. No action is performed if no CommandExecutor is bound
     * to parserResult.
     *
     * @param commandLineInterfaceCall aggregation of an end user call including cli definition and parser result
     * @throws CommandExecutorException if an error occurs while executing CommandExecutor
     */
    public void execute(CommandLineInterfaceCall commandLineInterfaceCall) throws CommandExecutorException {

        if (commandLineInterfaceCall == null) throw new IllegalArgumentException("Assertion failed. Method parameter 'parserResult' is null.");

        if (commandLineInterfaceCall.hasCommandExecutor()) {
            CommandExecutor commandExecutor = commandLineInterfaceCall.getCommandExecutor();
            commandExecutor.execute(commandLineInterfaceCall);
        }
    }

    private void assertNotCalledBefore() {
        if (this.calledBefore) throw new IllegalStateException(CommandLineInterface.class.getSimpleName() + ".parse called more than once.");
        calledBefore = true;
    }

}
