package de.arthurpicht.cli.print;

import de.arthurpicht.cli.CliDefinition;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.tree.CommandTreeNode;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.console.message.Message;
import de.arthurpicht.console.message.MessageBuilder;
import de.arthurpicht.console.message.format.Format;
import de.arthurpicht.utils.core.strings.Strings;

public class UsageMessage {

    public static Message getCommandSpecificUsage(CommandTreeNode commandTreeNode, CliDefinition cliDefinition, boolean indent, String label) {
        String executableName = cliDefinition.getCliDescription().getExecutableName();
        boolean hasGlobalOptions = cliDefinition.hasGlobalOptions();
        String commandsString = commandTreeNode.getCommandsString();
        boolean hasSpecificOptions = commandTreeNode.getCommand().getCommandTerminator().hasSpecificOptions();
        boolean hasParameters = commandTreeNode.getCommand().getCommandTerminator().hasParameters();

        MessageBuilder messageBuilder = new MessageBuilder();

        if (Strings.isSpecified(label))
            messageBuilder.addText(label);

        if (indent) messageBuilder.withIndentation(2);
        messageBuilder.addText(
                executableName,
                Format.BRIGHT_WHITE_TEXT());

        if (hasGlobalOptions) messageBuilder.addText(" [global options]");
        messageBuilder.addText(" " + commandsString, Format.BRIGHT_WHITE_TEXT());
        if (hasSpecificOptions) messageBuilder.addText(" [specific options]");

        if (hasParameters) {
            Parameters parameters = commandTreeNode.getCommand().getCommandTerminator().getParameters();
            messageBuilder
                    .addText(" ")
                    .addText(parameters.getHelpUsageSubString(), Format.BRIGHT_YELLOW_TEXT());
        }

        return messageBuilder.build();
    }

    public static Message getUsageOfDefaultCommand(
            CliDefinition cliDefinition,
            boolean defaultOnly,
            boolean indent) {

        String executableName = cliDefinition.getCliDescription().getExecutableName();
        boolean hasGlobalOptions = cliDefinition.hasGlobalOptions();
        DefaultCommand defaultCommand = cliDefinition.getDefaultCommand();

        MessageBuilder messageBuilder = new MessageBuilder();

        if (indent) messageBuilder.withIndentation(2);
        messageBuilder.addText(executableName, Format.BRIGHT_WHITE_TEXT());

        if (defaultOnly) {
            if (hasGlobalOptions) messageBuilder.addText(" [options]");
        } else {
            if (hasGlobalOptions) messageBuilder.addText(" [global options]");
        }

        if (defaultCommand.hasParameters()) {
            Parameters parameters = defaultCommand.getParameters();
            messageBuilder
                    .addText(" ")
                    .addText(parameters.getHelpUsageSubString(), Format.BRIGHT_YELLOW_TEXT());
        }

        return messageBuilder.build();
    }

}
