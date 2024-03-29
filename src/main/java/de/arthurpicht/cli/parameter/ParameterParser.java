package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CliResultBuilder;
import de.arthurpicht.cli.common.Parser;

import java.util.ArrayList;
import java.util.List;

public abstract class ParameterParser extends Parser {

    protected final List<String> parameterList;

    public ParameterParser(CliResultBuilder cliResultBuilder) {
        super(cliResultBuilder);
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
