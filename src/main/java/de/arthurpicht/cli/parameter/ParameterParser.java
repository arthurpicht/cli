package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.common.Parser;

import java.util.ArrayList;
import java.util.List;

public abstract class ParameterParser extends Parser {

    protected List<String> argumentList;

    public ParameterParser() {
        super();
        this.argumentList = new ArrayList<>();
    }

    public List<String> getArgumentList() {
        return this.argumentList;
    }

}
