package de.arthurpicht.cli.command;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.common.ParserResult;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandParserResult implements ParserResult {

    private final List<String> commandStringList;
    private final Options specificOptions;
    private final Parameters parameters;
    private final CommandExecutor commandExecutor;
    private final String description;

    public CommandParserResult() {
        this.commandStringList = Collections.unmodifiableList(new ArrayList<>());
        this.specificOptions = null;
        this.parameters = null;
        this.commandExecutor = null;
        this.description = null;
    }

    public CommandParserResult(List<String> commandStringList, Options specificOptions, Parameters parameters, CommandExecutor commandExecutor, String description) {
        this.commandStringList = Collections.unmodifiableList(commandStringList);
        this.specificOptions = specificOptions;
        this.parameters = parameters;
        this.commandExecutor = commandExecutor;
        this.description = description;
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

    public boolean hasDescription() {
        return Strings.isSpecified(this.description);
    }

    public String getDescription() {
        return description;
    }
}
