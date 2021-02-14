package de.arthurpicht.cli;

import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;

import java.util.Collections;
import java.util.List;

@Deprecated
public class CalledCommandSpec {

    private List<String> commandList;
    private Options optionsSpecific;
    private Parameters parameters;

    public CalledCommandSpec(List<String> commandList, Options optionsSpecific, Parameters parameters) {
        this.commandList = Collections.unmodifiableList(commandList);
        this.optionsSpecific = optionsSpecific;
        this.parameters = parameters;
    }

    public List<String> getCommandList() {
        return commandList;
    }

    public Options getOptionsSpecific() {
        return optionsSpecific;
    }

    public Parameters getParameters() {
        return parameters;
    }
}
