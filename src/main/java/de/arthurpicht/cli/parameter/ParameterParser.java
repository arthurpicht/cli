package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CommandLineInterfaceResultBuilder;
import de.arthurpicht.cli.common.Parser;

import java.util.ArrayList;
import java.util.List;

public abstract class ParameterParser extends Parser {

    protected List<String> parameterList;

    public ParameterParser(CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder) {
        super(commandLineInterfaceResultBuilder);
        this.parameterList = new ArrayList<>();
    }

    protected ParameterParserResult getParserResult() {
        return new ParameterParserResult(this.parameterList);
    }

    @Deprecated
    public List<String> getParameterList() {
        return this.parameterList;
    }

}
