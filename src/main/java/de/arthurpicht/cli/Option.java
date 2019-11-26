package de.arthurpicht.cli;

import java.util.Objects;

public class Option {

    private Character shortName;
    private String longName;
    private boolean hasArgument;
    private String helpText;

    public Option(Character shortName, String longName, boolean hasArgument, String helpText) {
        if (shortName != null && shortName == ' ') throw new IllegalArgumentException("Whitespace not allowed as shortName");
        if (shortName == null && isNullOrEmpty(longName))
            throw new IllegalArgumentException("At least one of shortName or longName must be specified.");

        this.shortName = shortName;
        if (!isNullOrEmpty(longName)) {
            this.longName = longName;
        } else {
            this.longName = null;
        }
        this.hasArgument = hasArgument;
        this.helpText = helpText;

    }

    public boolean hasShortName() {
        return this.shortName != null;
    }

    public boolean hasLongName() {
        return this.longName != null;
    }

    public Character getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }

    public boolean hasArgument() {
        return hasArgument;
    }

    public String getHelpText() {
        return helpText;
    }

    private static boolean isNullOrEmpty(String string) {
        return (string == null || string.equals(""));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return Objects.equals(shortName, option.shortName) &&
                Objects.equals(longName, option.longName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortName, longName);
    }

    public static void main(String[] args) {
        new Option(null, "", true, "help");
    }
}
