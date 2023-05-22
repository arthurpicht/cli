package de.arthurpicht.cli;

public interface CommandExecutor {

    void execute(CliCall cliCall) throws CommandExecutorException;

}
