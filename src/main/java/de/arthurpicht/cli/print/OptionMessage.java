package de.arthurpicht.cli.print;

import de.arthurpicht.cli.option.Option;
import de.arthurpicht.console.message.Message;
import de.arthurpicht.console.message.MessageBuilder;
import de.arthurpicht.console.message.format.BlockFormat;
import de.arthurpicht.console.message.format.Format;

public class OptionMessage {

    public static Message asMessage(Option option) {
        MessageBuilder messageBuilder = new MessageBuilder()
                .withIndentation(2);

        int shortNameOverlength = 0;

        if (option.hasShortName()) {
            String shortNameToken = getShortNameToken(option);
            messageBuilder.addText(getShortNameToken(option), Format.BRIGHT_YELLOW_TEXT());
            shortNameOverlength = shortNameToken.length() - 2;

        } else {
            messageBuilder.addText("  ");
        }

        if (option.hasShortName() && option.hasLongName()) {
            messageBuilder.addText(", ");
        } else {
            messageBuilder.addText("  ");
        }

        String longNameToken = getLongNameToken(option);
        int width = 25 - shortNameOverlength;
        if (width < 0) width = 0;

        messageBuilder.addText(
                longNameToken,
                Format.BRIGHT_YELLOW_TEXT(),
                new BlockFormat.Builder(    )
                        .withWidth(width)
                        .withOverflowStrategy(BlockFormat.OverflowStrategy.EXPAND)
                        .build()
        );

        if (option.hasHelpText())
            messageBuilder.addText(" " + option.getDescription());

        return messageBuilder.build();
    }

    private static String getShortNameToken(Option option) {
        String token = "-" + option.getShortName();
        if (!option.hasLongName() && option.hasArgumentName()) {
            token = token + " " + getArgumentToken(option);
        }
        return token;
    }

    private static String getLongNameToken(Option option) {
        if (!option.hasLongName()) return "";
        String token = "--" + option.getLongName();
        if (option.hasArgument()) {
            token = token + " " + getArgumentToken(option);
        }
        return token;
    }

    private static String getArgumentToken(Option option) {
        if (!option.hasArgument()) throw new IllegalStateException();
        String argumentName = option.hasArgumentName() ? option.getArgumentName() : "arg";
        return "<" + argumentName + ">";
    }

}
