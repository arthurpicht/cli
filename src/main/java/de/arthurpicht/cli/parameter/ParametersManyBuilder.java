package de.arthurpicht.cli.parameter;

import de.arthurpicht.utils.core.strings.Strings;

import java.util.ArrayList;
import java.util.List;

public class ParametersManyBuilder {

    private String genericName;
    private final List<String> nameList;
    private final List<String> descriptionList;

    public ParametersManyBuilder() {
        this.genericName = "";
        this.nameList = new ArrayList<>();
        this.descriptionList = new ArrayList<>();
    }

    public ParametersManyBuilder withGenericName(String genericName) {
        if (Strings.isUnspecified(genericName)) throw new IllegalArgumentException("Parameter is unspecified.");
        this.genericName = genericName;
        return this;
    }

    public ParametersManyBuilder addParameter() {
        this.nameList.add("");
        this.descriptionList.add("");
        return this;
    }

    public ParametersManyBuilder addParameter(String name) {
        if (Strings.isUnspecified(name)) throw new IllegalArgumentException("Parameter is unspecified.");
        this.nameList.add(name);
        this.descriptionList.add("");
        return this;
    }

    public ParametersManyBuilder addParameter(String name, String description) {
        if (Strings.isUnspecified(name)) throw new IllegalArgumentException("Parameter is unspecified.");
        if (Strings.isUnspecified(description)) throw new IllegalArgumentException("Parameter is unspecified.");
        this.nameList.add(name);
        this.descriptionList.add(description);
        return this;
    }

    public ParametersManyBuilder addParameterByDescriptionOnly(String description) {
        if (Strings.isUnspecified(description)) throw new IllegalArgumentException("Parameter is unspecified.");
        this.nameList.add("");
        this.descriptionList.add(description);
        return this;
    }

    public ParametersMany build() {
        List<Parameter> parameterList = new ArrayList<>();
        for (int i = 0; i < this.nameList.size(); i++) {
            String name = this.nameList.get(i);
            if (Strings.isUnspecified(name)) name = this.genericName + "-" + (i + 1);
            String description = this.descriptionList.get(i);
            parameterList.add(new Parameter(name, description));
        }

        return new ParametersMany(parameterList);
    }

}
