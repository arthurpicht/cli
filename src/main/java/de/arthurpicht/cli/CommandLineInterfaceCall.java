package de.arthurpicht.cli;

import de.arthurpicht.cli.CommandLineInterfaceResult.Status;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.parameter.ParameterParserResult;

import java.util.List;

/**
 * Representation of an end user call to command line interface. An instance is
 * created by cli {@link CommandLineInterface} when given end user arguments
 * could be recognized as syntactically correct and parsed successfully.
 * Contains specified arguments, {@link CommandLineInterfaceDefinition} and
 * {@link CommandLineInterfaceResult}.
 */
public class CommandLineInterfaceCall {

    private final List<String> args;
    private final CommandLineInterfaceDefinition commandLineInterfaceDefinition;
    private final CommandLineInterfaceResult commandLineInterfaceResult;

    public CommandLineInterfaceCall(
            String[] args,
            CommandLineInterfaceDefinition commandLineInterfaceDefinition,
            CommandLineInterfaceResult commandLineInterfaceResult
    ) {
        if (args == null) throw new IllegalArgumentException("Method parameter is null.");
        if (commandLineInterfaceDefinition == null) throw new IllegalArgumentException("Method parameter is null.");
        if (commandLineInterfaceResult == null) throw new IllegalArgumentException("Method parameter is null.");

        this.args = List.of(args);
        this.commandLineInterfaceDefinition = commandLineInterfaceDefinition;
        this.commandLineInterfaceResult = commandLineInterfaceResult;
    }

    /**
     * Arguments to executable as called by end-user.
     *
     * @return arguments of executable
     */
    public List<String> getArgs() {
        return args;
    }

    /**
     * Definition of command line interface.
     *
     * @return Definition of command line interface.
     */
    public CommandLineInterfaceDefinition getCommandLineInterfaceDefinition() {
        return commandLineInterfaceDefinition;
    }

    /**
     * Aggregated parsed arguments from command line interface.
     *
     * @return aggregated parsed arguments as given by end user
     */
    public CommandLineInterfaceResult getCommandLineInterfaceResult() {
        return commandLineInterfaceResult;
    }

    /**
     * A convenience (shortcut) method that returns parsed global options as given by end user.
     *
     * @return global options as given by end user
     */
    public OptionParserResult getOptionParserResultGlobal() {
        return this.commandLineInterfaceResult.getOptionParserResultGlobal();
    }

    /**
     * A convenience (shortcut) method that returns parsed commands as given by end user.
     *
     * @return commands as given by end user
     */
    public List<String> getCommandList() {
        return this.commandLineInterfaceResult.getCommandParserResult().getCommandStringList();
    }

    /**
     * A convenience (shortcut) method that returns parsed specific options as given by end user.
     * If no options are given by end user, {@link OptionParserResult} is empty.
     *
     * @return specific options as given by end user
     */
    public OptionParserResult getOptionParserResultSpecific() {
        return this.commandLineInterfaceResult.getOptionParserResultSpecific();
    }

    /**
     * A convenience (shortcut) method that returns parsed parameters as given by end user.
     * If no options are given by end user, {@link ParameterParserResult} is empty.
     *
     * @return parameters as given by end user
     */
    public ParameterParserResult getParameterParserResult() {
        return this.commandLineInterfaceResult.getParameterParserResult();
    }

    /**
     * A convenience (shortcut) method that returns true if a {@link CommandExecutor} exists
     * for command given by end user.
     *
     * @return true if exists a {@link CommandExecutor}
     */
    public boolean hasCommandExecutor() {
        return this.commandLineInterfaceResult.getCommandParserResult().hasCommandExecutor();
    }

    /**
     * A convenience (shortcut) method that returns the command executor associated to
     * command given by end user. If no such {@link CommandExecutor} exists null is returned.
     *
     * @return CommandExecutor as associated to command given by end user.
     */
    public CommandExecutor getCommandExecutor() {
        return this.commandLineInterfaceResult.getCommandParserResult().getCommandExecutor();
    }

    /**
     * A convenience (shortcut) method that return the status of the parsing result.
     *
     * @return status of parsing result
     */
    public Status getStatus() {
        return this.commandLineInterfaceResult.getStatus();
    }

}
