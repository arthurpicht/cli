package de.arthurpicht.cli;

public class CliDescriptionBuilder {

    private String description;
    private String versionText;
    private String versionTextSupplement;

    public CliDescriptionBuilder() {
        this.description = "";
        this.versionText = "";
        this.versionTextSupplement = "";
    }

    public CliDescriptionBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CliDescriptionBuilder withVersionText(String versionText) {
        this.versionText = versionText;
        return this;
    }

    public CliDescriptionBuilder withVersionByTag(String versionTag) {
        this.versionText = "version " + versionTag;
        return this;
    }

    public CliDescriptionBuilder withVersionByTag(String versionTag, String date) {
        this.versionText = "version " + versionTag + " from " + date;
        return this;
    }

    public CliDescriptionBuilder withVersionByTag(String versionTag, String date, String supplement) {
        this.versionText = "version " + versionTag + " from " + date;
        this.versionTextSupplement = supplement;
        return this;
    }

    public CliDescription build(String executableName) {
        return new CliDescription(
                executableName,
                this.description,
                this.versionText,
                this.versionTextSupplement
        );
    }

}
