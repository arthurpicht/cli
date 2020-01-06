package de.arthurpicht.cli.argument;

import de.arthurpicht.cli.common.CLIAbstractParser;

import java.util.ArrayList;
import java.util.List;

public abstract class ArgumentParser extends CLIAbstractParser {

    protected List<String> argumentList;

    public ArgumentParser() {
        super();
        this.argumentList = new ArrayList<>();
    }

    public List<String> getArgumentList() {
        return this.argumentList;
    }

}
