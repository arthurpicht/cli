package de.arthurpicht.cli.option;

import de.arthurpicht.console.message.Message;
import de.arthurpicht.console.message.MessageBuilder;
import de.arthurpicht.console.message.format.BlockFormat;
import de.arthurpicht.console.message.format.Format;
import de.arthurpicht.utils.core.assertion.MethodPreconditions;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.Objects;

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
    @SuppressWarnings("JavaDoc")
    public Option(String id, Character shortName, String longName, boolean hasArgument, String argumentName, String description) {
        MethodPreconditions.assertArgumentNotNullAndNotEmpty("id", id);
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

    public Message getHelpMessage() {
        MessageBuilder messageBuilder = new MessageBuilder()
                .withIndentation(2);

        if (hasShortName()) {
            messageBuilder.addText("-" + this.shortName, Format.BRIGHT_YELLOW_TEXT());
        } else {
            messageBuilder.addText("  ");
        }

        if (hasShortName() && hasLongName()) {
            messageBuilder.addText(", ");
        } else {
            messageBuilder.addText("  ");
        }

        String longNameSwitch = getLongNameSwitch();
        messageBuilder.addText(
                longNameSwitch,
                Format.BRIGHT_YELLOW_TEXT(),
                new BlockFormat.Builder(25)
                        .withOverflowStrategy(BlockFormat.OverflowStrategy.EXPAND)
                        .build()
        );

        if (hasHelpText())
            messageBuilder.addText(" " + getDescription());

        return messageBuilder.build();
    }

    private String getLongNameSwitch() {
        String string = hasLongName() ? "--" + getLongName() : "";
        if (hasArgument()) {
            String argumentName = hasArgumentName() ? this.argumentName : "arg";
            string = string + " <" + argumentName + ">";
        }
        return string;
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
