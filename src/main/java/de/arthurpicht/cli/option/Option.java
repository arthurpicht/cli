package de.arthurpicht.cli.option;

import java.util.Objects;

public class Option {

    private String id;
    private Character shortName;
    private String longName;
    private boolean hasArgument;
    private String helpText;

    public Option(String id, Character shortName, String longName, boolean hasArgument, String helpText) {

        if (id == null || id.equals("")) throw new IllegalArgumentException("Null or empty id.");
        if (shortName != null && shortName == ' ') throw new IllegalArgumentException("Null or empty shortName");
        if (shortName == null && isNullOrEmpty(longName))
            throw new IllegalArgumentException("At least one of shortName or longName must be specified.");
        if (longName.contains(" ")) throw new IllegalArgumentException("longName contains space.");

        this.id = id;
        this.shortName = shortName;
        if (!isNullOrEmpty(longName)) {
            this.longName = longName;
        } else {
            this.longName = null;
        }
        this.hasArgument = hasArgument;
        this.helpText = helpText;

    }

    public String getId() {
        return id;
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
        return id.equals(option.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static void main(String[] args) {
        new Option("myId",null, "", true, "help");
    }
}
