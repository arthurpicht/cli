package de.arthurpicht.cli;

import de.arthurpicht.cli.CliResult.Status;
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

import static de.arthurpicht.cli.CliResult.Status.COMPLETE;

public class Cli {

    private final CliDefinition cliDefinition;
    private final CliResultBuilder cliResultBuilder;
    private boolean calledBefore;

    /**
     * Initializes CLI. Usage for end user is discouraged. Use {@link CliBuilder}
     * instead.
     *
     * @param cliDefinition
     */
    public Cli(CliDefinition cliDefinition) {

        if (cliDefinition == null)
            throw new IllegalArgumentException("Parameter 'CliDefinition' is null.");

        this.cliDefinition = cliDefinition;
        this.cliResultBuilder = new CliResultBuilder();
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
    public CliCall parse(String[] args) throws UnrecognizedArgumentException {

        assertNotCalledBefore();

        CliResult cliResult;

        try {
            cliResult = parseCore(args);
        } catch (ParsingBrokenEvent parsingBrokenEvent) {
            cliResult = parsingBrokenEvent.getCliResult();
        }

        return new CliCall(
                args,
                this.cliDefinition,
                cliResult
        );
    }

    private CliResult parseCore(String[] args) throws UnrecognizedArgumentException, ParsingBrokenEvent {

        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        parseGlobalOptions(argumentIterator);

        parseCommands(argumentIterator);

        parseSpecificOptions(argumentIterator);

        parseParameters(argumentIterator);

        handleSurplusArguments(argumentIterator);

        return cliResultBuilder.build(COMPLETE);
    }

    private void parseGlobalOptions(ArgumentIterator argumentIterator) throws ParsingBrokenEvent, UnrecognizedArgumentException {
        if (this.cliDefinition.hasGlobalOptions()) {
            OptionParser optionParserGlobal = new OptionParser(
                    OptionParser.Target.GLOBAL,
                    this.cliDefinition.getGlobalOptions(),
                    this.cliResultBuilder,
                    this.cliDefinition.getCliDescription().getExecutableName());
            optionParserGlobal.parse(argumentIterator);
        }
    }

    private void parseCommands(ArgumentIterator argumentIterator) throws InsufficientNrOfCommandsException, IllegalCommandException, AmbiguousCommandException {

        if (this.cliDefinition.hasCommands()) {
            CommandParser commandParser = new CommandParser(
                    this.cliDefinition.getCommandTree(),
                    this.cliDefinition.getDefaultCommand(),
                    this.cliResultBuilder,
                    this.cliDefinition.getCliDescription().getExecutableName());
            commandParser.parse(argumentIterator);
        } else {
            if (this.cliDefinition.hasDefaultCommand()) {
                DefaultCommand defaultCommand = this.cliDefinition.getDefaultCommand();
                this.cliResultBuilder.withCommandParserResult(
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
        if (this.cliResultBuilder.hasSpecificOptions()) {
            Options optionsSpecific = this.cliResultBuilder.getSpecificOptions();
            OptionParser optionParserSpecific = new OptionParser(
                    OptionParser.Target.SPECIFIC,
                    optionsSpecific,
                    this.cliResultBuilder,
                    this.cliDefinition.getCliDescription().getExecutableName());
            optionParserSpecific.parse(argumentIterator);
        }
    }

    private void parseParameters(ArgumentIterator argumentIterator) throws ParsingBrokenEvent, UnrecognizedArgumentException {
        if (this.cliResultBuilder.hasParameters()) {
            Parameters parameters = this.cliResultBuilder.getParameters();
            String executableName = this.cliDefinition.getCliDescription().getExecutableName();
            ParameterParser parameterParser = parameters.getParameterParser(this.cliResultBuilder, executableName);
            parameterParser.parse(argumentIterator);
        }
    }

    private void handleSurplusArguments(ArgumentIterator argumentIterator) throws UnrecognizedArgumentException {
        if (argumentIterator.hasNext()) {
            String arg = argumentIterator.getNext();
            String executableName = this.cliDefinition.getCliDescription().getExecutableName();
            throw new UnrecognizedArgumentException(executableName, argumentIterator, "Unrecognized argument: " + arg);
        }
    }

    /**
     * Parses specified arguments against cli specification and executes CommandExecutor, if found.
     *
     * @param args cli arguments
     * @return cliCall
     * @throws UnrecognizedArgumentException if an argument can not be recognized as an syntactical element of the cli
     */
    public CliCall execute(String[] args) throws UnrecognizedArgumentException, CommandExecutorException {

        if (args == null) throw new IllegalArgumentException("Assertion failed. Method parameter 'args' is null.");

        CliCall cliCall = this.parse(args);
        execute(cliCall);
        return cliCall;
    }

    /**
     * Executes CommandExecutor for pre-parsed arguments. No action is performed if no CommandExecutor is bound
     * to parserResult.
     *
     * @param cliCall aggregation of an end user call including cli definition and parser result
     * @throws CommandExecutorException if an error occurs while executing CommandExecutor
     */
    public void execute(CliCall cliCall) throws CommandExecutorException {
        if (cliCall == null) throw new IllegalArgumentException("Assertion failed. Method parameter 'parserResult' is null.");

        if (cliCall.getStatus() == Status.BROKEN) {
            GenericCommandExecutor.apply(cliCall);
        } else if (cliCall.hasCommandExecutor()) {
            CommandExecutor commandExecutor = cliCall.getCommandExecutor();
            commandExecutor.execute(cliCall);
        }
    }

    private void assertNotCalledBefore() {
        if (this.calledBefore) throw new IllegalStateException(Cli.class.getSimpleName() + ".parse called more than once.");
        calledBefore = true;
    }

}
