package de.arthurpicht.cli;

public class CliDescriptionBuilder {

    private String description;
    private String version;
    private String date;

    public CliDescriptionBuilder() {
        this.description = "";
        this.version = "";
        this.date = "";
    }

    public CliDescriptionBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CliDescriptionBuilder withVersion(String version) {
        this.version = version;
        return this;
    }

    public CliDescriptionBuilder withDate(String date) {
        this.date = date;
        return this;
    }

    public CliDescription build(String executableName) {
        return new CliDescription(
                executableName,
                this.description,
                this.version,
                this.date
        );
    }

}
