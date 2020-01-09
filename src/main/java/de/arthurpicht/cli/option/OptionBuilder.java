package de.arthurpicht.cli.option;

public class OptionBuilder {

    private Character shortName;
    private String longName;
    private boolean hasArgument;
    private String argumentName;
    private String description;

    public OptionBuilder() {
        this.shortName = null;
        this.longName = null;
        this.hasArgument = false;
        this.argumentName = null;
        this.description = "";
    }

    public OptionBuilder getInstance() {
        return new OptionBuilder();
    }

    public OptionBuilder withShortName(char shortName) {
        this.shortName = shortName;
        return this;
    }

    public OptionBuilder withLongName(String longName) {
        this.longName = longName;
        return this;
    }

    public OptionBuilder hasArgument() {
        this.hasArgument = true;
        return this;
    }

    public OptionBuilder withArgumentName(String argumentName) {
        this.argumentName = argumentName;
        return this;
    }

    public OptionBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public Option build(String id) {
        return new Option(id, this.shortName, this.longName, this.hasArgument, this.argumentName, this.description);
    }

}
