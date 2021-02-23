package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.help.HelpFormatterCommons;
import de.arthurpicht.utils.core.strings.Strings;

public class Parameter {

    public static final String DEFAULT_NAME = "parameter";

    private final String name;
    private final String description;

    public Parameter(String name, String description) {
        this.name = Strings.isSpecified(name) ? name : DEFAULT_NAME;
        this.description = Strings.isSpecified(description) ? description : "";
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsageString() {
        return "<" + this.name + ">";
    }

    public String getHelpString() {
        return HelpFormatterCommons.formatStringsToCols(getUsageString(), this.getDescription());
    }

}
