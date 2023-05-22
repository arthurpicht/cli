package de.arthurpicht.cli.print;

import de.arthurpicht.cli.parameter.Parameter;
import de.arthurpicht.cli.parameter.ParametersN;
import de.arthurpicht.console.message.Message;
import de.arthurpicht.console.message.MessageBuilder;
import de.arthurpicht.console.message.format.Format;

public class ParametersNMessage {

    public static Message asMessage(ParametersN parametersN) {
        MessageBuilder messageBuilder = new MessageBuilder();
        boolean first = true;
        for (Parameter parameter : parametersN.getParameterList()) {
            if (!first) messageBuilder.addText("\n");
            messageBuilder.addText("  ");
            messageBuilder.addText(parameter.getUsageString(), Format.BRIGHT_YELLOW_TEXT(), Format.BLOCK_EXPANDED(30));
            if (parameter.hasDescription()) messageBuilder.addText(parameter.getDescription());
            first = false;
        }
        return messageBuilder.build();
    }

}
