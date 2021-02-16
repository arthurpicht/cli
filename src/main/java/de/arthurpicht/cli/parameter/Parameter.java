package de.arthurpicht.cli.parameter;

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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getUsageString());
        if (Strings.isSpecified(this.description)) {
            Strings.fillUpAfter(stringBuilder, ' ', 25);
            stringBuilder.append(this.description);
        }
        return stringBuilder.toString();
    }
}
