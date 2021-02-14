package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.common.ParserResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParameterParserResult implements ParserResult {

    private final List<String> parameterList;

    public ParameterParserResult() {
        this.parameterList = Collections.unmodifiableList(new ArrayList<>());
    }

    public ParameterParserResult(List<String> parameterList) {
        this.parameterList = Collections.unmodifiableList(parameterList);
    }

    public boolean isEmpty() {
        return this.parameterList.isEmpty();
    }

    public int getNrOfParameters() {
        return this.parameterList.size();
    }

    public List<String> getParameterList() {
        return parameterList;
    }
}
