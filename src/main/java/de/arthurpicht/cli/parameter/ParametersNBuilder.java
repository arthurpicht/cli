package de.arthurpicht.cli.parameter;

import de.arthurpicht.utils.core.strings.Strings;

import java.util.ArrayList;
import java.util.List;

public class ParametersNBuilder {

    private String genericName;
    private final List<String> nameList;
    private final List<String> descriptionList;

    public ParametersNBuilder() {
        this.genericName = "";
        this.nameList = new ArrayList<>();
        this.descriptionList = new ArrayList<>();
    }

    public ParametersNBuilder withGenericName(String genericName) {
        if (Strings.isUnspecified(genericName)) throw new IllegalArgumentException("Parameter is unspecified.");
        this.genericName = genericName;
        return this;
    }

    public ParametersNBuilder addParameter() {
        this.nameList.add("");
        this.descriptionList.add("");
        return this;
    }

    public ParametersNBuilder addParameter(String name) {
        if (Strings.isUnspecified(name)) throw new IllegalArgumentException("Parameter is unspecified.");
        this.nameList.add(name);
        this.descriptionList.add("");
        return this;
    }

    public ParametersNBuilder addParameter(String name, String description) {
        if (Strings.isUnspecified(name)) throw new IllegalArgumentException("Parameter is unspecified.");
        if (Strings.isUnspecified(description)) throw new IllegalArgumentException("Parameter is unspecified.");
        this.nameList.add(name);
        this.descriptionList.add(description);
        return this;
    }

    public ParametersNBuilder addParameterByDescriptionOnly(String description) {
        if (Strings.isUnspecified(description)) throw new IllegalArgumentException("Parameter is unspecified.");
        this.nameList.add("");
        this.descriptionList.add(description);
        return this;
    }

    public ParametersN build() {
        if (Strings.isUnspecified(this.genericName)) this.genericName = Parameter.DEFAULT_NAME;
        List<Parameter> parameterList = new ArrayList<>();
        for (int i = 0; i < this.nameList.size(); i++) {
            String name = this.nameList.get(i);
            if (Strings.isUnspecified(name)) name = this.genericName + "-" + (i + 1);
            String description = this.descriptionList.get(i);
            parameterList.add(new Parameter(name, description));
        }

        return new ParametersN(parameterList);
    }

}
