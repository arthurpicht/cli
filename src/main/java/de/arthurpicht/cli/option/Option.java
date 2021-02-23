package de.arthurpicht.cli.option;

import de.arthurpicht.utils.core.strings.Strings;

import java.util.Objects;

import static de.arthurpicht.cli.help.HelpFormatterCommons.COL_WIDTH;

public class Option {

    private final String id;
    private final Character shortName;
    private final String longName;
    private final boolean hasArgument;
    private final String argumentName;
    private final String description;
    private final boolean breaking;

    /**
     * Usage of Option constructor is discouraged as signature will change in further development.
     * Use OptionBuilder instead.
     *
     * @param id
     * @param shortName
     * @param longName
     * @param hasArgument
     * @param argumentName
     * @param description
     */
    public Option(String id, Character shortName, String longName, boolean hasArgument, String argumentName, String description) {

        if (id == null || id.equals("")) throw new IllegalArgumentException("Null or empty id.");
        if (shortName != null && shortName == ' ') throw new IllegalArgumentException("Null or empty shortName");
        if (shortName == null && Strings.isNullOrEmpty(longName))
            throw new IllegalArgumentException("At least one of shortName or longName must be specified.");
        if (longName != null && longName.contains(" ")) throw new IllegalArgumentException("longName contains space.");

        this.id = id;
        this.shortName = shortName;
        if (!Strings.isNullOrEmpty(longName)) {
            this.longName = longName;
        } else {
            this.longName = null;
        }
        this.hasArgument = hasArgument;
        this.argumentName = argumentName;
        this.description = description;
        this.breaking = false;

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

    public boolean hasArgumentName() {
        return (this.argumentName != null && !this.argumentName.equals(""));
    }

    public String getArgumentName() {
        return this.argumentName;
    }

    public boolean hasHelpText() {
        return (this.description != null && !this.description.equals(""));
    }

    public String getDescription() {
        return description;
    }

    public boolean isBreaking() {
        return this.breaking;
    }

    public String getHelpString() {

        StringBuilder stringBuilder = new StringBuilder();

        if (hasShortName()) {
            stringBuilder.append("-").append(this.shortName);
        } else {
            stringBuilder.append("  ");
        }

        if (hasShortName() && hasLongName()) {
            stringBuilder.append(", ");
        } else {
            stringBuilder.append("  ");
        }

        if (hasLongName()) {
            stringBuilder.append("--").append(getLongName());
        }

        if (hasArgument()) {
            String argumentName = hasArgumentName() ? this.argumentName : "arg";
            stringBuilder.append(" <").append(argumentName).append(">");
        }

        // TODO Blocksatz, Zeilenumbruch helptext s.a. https://commons.apache.org/proper/commons-cli/

        if (hasHelpText()) {
            Strings.fillUpAfter(stringBuilder, ' ', COL_WIDTH);
            stringBuilder.append(getDescription());
        }

        return stringBuilder.toString();
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

    @Override
    public String toString() {
        return "Option{" +
                "id='" + id + '\'' +
                ", shortName=" + shortName +
                ", longName='" + longName + '\'' +
                ", hasArgument=" + hasArgument +
                ", argumentName='" + argumentName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
