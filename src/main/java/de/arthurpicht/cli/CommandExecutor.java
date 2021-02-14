package de.arthurpicht.cli;

import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.parameter.ParameterParserResult;

import java.util.List;

public interface CommandExecutor {

    public void execute(
            OptionParserResult optionParserResultGlobal,
            List<String> commandList,
            OptionParserResult optionParserResultSpecific,
            ParameterParserResult parameterParserResult
    ) throws CommandExecutorException;

}
