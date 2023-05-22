package de.arthurpicht.cli.print;

import de.arthurpicht.cli.parameter.ParametersOne;
import de.arthurpicht.console.message.Message;
import de.arthurpicht.console.message.MessageBuilder;
import de.arthurpicht.console.message.format.Format;

public class ParametersOneMessage {

    public static Message asMessage(ParametersOne parametersOne) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.addText("  ");
        messageBuilder.addText(
                parametersOne.getHelpUsageSubString(),
                Format.BRIGHT_YELLOW_TEXT(),
                Format.BLOCK_EXPANDED(30));
        messageBuilder.addText(parametersOne.getDescription());
        return messageBuilder.build();
    }

}
