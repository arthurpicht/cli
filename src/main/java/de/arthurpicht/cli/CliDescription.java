package de.arthurpicht.cli;

import de.arthurpicht.utils.core.strings.Strings;

public class CliDescription {

    private final String executableName;
    private final String description;
    private final String versionText;
    private final String versionTextSupplement;

    public CliDescription(String executableName) {
        this.executableName = executableName;
        this.description = "";
        this.versionText = "";
        this.versionTextSupplement = "";
    }

    public CliDescription(
            String executableName,
            String description,
            String versionText,
            String versionTextSupplement
    ) {
        this.executableName = executableName;
        this.description = description;
        this.versionText = versionText;
        this.versionTextSupplement = versionTextSupplement;
    }

    public String getExecutableName() {
        return this.executableName;
    }

    public boolean hasDescription() {
        return Strings.isSpecified(this.description);
    }

    public String getDescription() {
        return this.description;
    }

    public String getDescriptionFirstLine() {
        if (!hasDescription()) throw new IllegalStateException("No description specified.");
        return this.description.lines().findFirst().get();
    }

    public boolean hasVersionText() {
        return Strings.isSpecified(this.versionText);
    }

    public String getVersionText() {
        return this.versionText;
    }

    public boolean hasVersionTextSupplement() {
        return Strings.isSpecified(this.versionTextSupplement);
    }

    public String getVersionTextSupplement() {
        return this.versionTextSupplement;
    }

}
