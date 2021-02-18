package de.arthurpicht.cli;

public class CommandLineInterfaceDescriptionBuilder {

    private final String executableName;
    private String description;
    private String version;
    private String date;

    public CommandLineInterfaceDescriptionBuilder(String executableName) {
        this.executableName = executableName;
        this.description = "";
        this.version = "";
        this.date = "";
    }

    public CommandLineInterfaceDescriptionBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CommandLineInterfaceDescriptionBuilder withVersion(String version) {
        this.version = version;
        return this;
    }

    public CommandLineInterfaceDescriptionBuilder withDate(String date) {
        this.date = date;
        return this;
    }

    public CommandLineInterfaceDescription build() {
        return new CommandLineInterfaceDescription(
                this.executableName,
                this.description,
                this.version,
                this.date
        );
    }

}
