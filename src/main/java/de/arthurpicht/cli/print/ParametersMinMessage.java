package de.arthurpicht.cli.print;

import de.arthurpicht.cli.parameter.ParametersMin;
import de.arthurpicht.console.message.Message;
import de.arthurpicht.console.message.MessageBuilder;
import de.arthurpicht.console.message.format.Format;

public class ParametersMinMessage {

    public static Message asMessage(ParametersMin parametersMin) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.addText(parametersMin.getHelpUsageSubString(), Format.BRIGHT_YELLOW_TEXT(), Format.BLOCK_EXPANDED(30));
        if (parametersMin.hasDescription())  messageBuilder.addText(parametersMin.getDescription());
        return messageBuilder.build();
    }

}
