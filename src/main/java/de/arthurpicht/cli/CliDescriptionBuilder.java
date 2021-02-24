package de.arthurpicht.cli;

public class CliDescriptionBuilder {

    private final String executableName;
    private String description;
    private String version;
    private String date;

    public CliDescriptionBuilder(String executableName) {
        this.executableName = executableName;
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

    public CliDescription build() {
        return new CliDescription(
                this.executableName,
                this.description,
                this.version,
                this.date
        );
    }

}
