package de.arthurpicht.cli;

import de.arthurpicht.utils.core.strings.Strings;

public class CliDescription {

    private final String executableName;
    private final String description;
    private final String version;
    private final String date;

    public CliDescription(String executableName) {
        this.executableName = executableName;
        this.description = "";
        this.version = "";
        this.date = "";
    }

    public CliDescription(
            String executableName,
            String description,
            String version,
            String date
    ) {
        this.executableName = executableName;
        this.description = description;
        this.version = version;
        this.date = date;
    }

    public String getExecutableName() {
        return executableName;
    }

    public boolean hasDescription() {
        return Strings.isSpecified(this.description);
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionFirstLine() {
        if (!hasDescription()) throw new IllegalStateException("No description specified.");
        return this.description.lines().findFirst().get();
    }

    public boolean hasVersion() {
        return Strings.isSpecified(this.version);
    }

    public String getVersion() {
        return version;
    }

    public boolean hasDate() {
        return Strings.isSpecified(this.date);
    }

    public String getDate() {
        return date;
    }
}
