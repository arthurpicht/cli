package de.arthurpicht.cli;

public interface CommandExecutor {

    public void execute(CliCall cliCall) throws CommandExecutorException;

}
