package de.arthurpicht.cli;

import de.arthurpicht.cli.option.OptionParserResult;

import java.util.List;

public interface CommandExecutor {

    public void execute(
            OptionParserResult optionParserResultGlobal,
            List<String> commandList,
            OptionParserResult optionParserResultSpecific,
            List<String> parameterList
    ) throws CommandExecutorException;

}
