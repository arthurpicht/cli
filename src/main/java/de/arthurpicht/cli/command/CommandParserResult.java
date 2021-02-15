package de.arthurpicht.cli.command;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.common.ParserResult;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandParserResult implements ParserResult {

    private final List<String> commandStringList;
    private final Options specificOptions;
    private final Parameters parameters;
    private final CommandExecutor commandExecutor;

    public CommandParserResult() {
        this.commandStringList = Collections.unmodifiableList(new ArrayList<>());
        this.specificOptions = null;
        this.parameters = null;
        this.commandExecutor = null;
    }

    public CommandParserResult(List<String> commandStringList, Options specificOptions, Parameters parameters, CommandExecutor commandExecutor) {
        this.commandStringList = Collections.unmodifiableList(commandStringList);
        this.specificOptions = specificOptions;
        this.parameters = parameters;
        this.commandExecutor = commandExecutor;
    }

    public List<String> getCommandStringList() {
        return commandStringList;
    }

    public boolean hasSpecificOptions() {
        return this.specificOptions != null && !this.specificOptions.isEmpty();
    }

    public Options getSpecificOptions() {
        return specificOptions;
    }

    public boolean hasParameters() {
        return this.parameters != null;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public boolean hasCommandExecutor() {
        return this.commandExecutor != null;
    }

    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }
}
