package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CommandLineInterfaceDefinition;
import de.arthurpicht.cli.CommandLineInterfaceDescription;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.tree.CommandTreeNode;
import de.arthurpicht.cli.common.CLIContext;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.utils.core.strings.Strings;

import java.io.PrintStream;

public class HelpFormatterCommons {

    public static final String INDENT = "  ";
    public static final int COL_WIDTH = 30;

    public static String getHeaderString(CommandLineInterfaceDefinition cliDefinition) {

        String executableName = cliDefinition.getCommandLineInterfaceDescription().getExecutableName();
        CommandLineInterfaceDescription cliDescription = cliDefinition.getCommandLineInterfaceDescription();

        String header = executableName;
        if (cliDescription.hasVersion()) {
            header += " " + cliDescription.getVersion();
        }
        if (cliDescription.hasDate()) {
            header += " from " + cliDescription.getDate();
        }

        return header;
    }

    public static void printHeaderString(CommandLineInterfaceDefinition commandLineInterfaceDefinition) {
        CLIContext.out.println(HelpFormatterCommons.getHeaderString(commandLineInterfaceDefinition));
    }

    public static void printExecutableDescription(CommandLineInterfaceDescription commandLineInterfaceDescription) {

        if (commandLineInterfaceDescription.hasDescription()) {
            String description = commandLineInterfaceDescription.getDescription();
            CLIContext.out.println(HelpFormatterCommons.indentString(description));
        }
    }

    public static String getUsageOfDefaultCommand(
            CommandLineInterfaceDefinition commandLineInterfaceDefinition,
            boolean defaultOnly) {

        String executableName = commandLineInterfaceDefinition.getCommandLineInterfaceDescription().getExecutableName();
        boolean hasGlobalOptions = commandLineInterfaceDefinition.hasGlobalOptions();
        DefaultCommand defaultCommand = commandLineInterfaceDefinition.getDefaultCommand();

        String usage = executableName;

        if (defaultOnly) {
            if (hasGlobalOptions) usage += " [options]";
        } else {
            if (hasGlobalOptions) usage += " [global options]";
        }

        if (defaultCommand.hasParameters()) {
            Parameters parameters = defaultCommand.getParameters();
            usage += " " + parameters.getHelpUsageSubString();
        }

        return usage;
    }

    public static String getCommandSpecificUsage(CommandTreeNode commandTreeNode, CommandLineInterfaceDefinition cliDefinition) {

        String executableName = cliDefinition.getCommandLineInterfaceDescription().getExecutableName();
        boolean hasGlobalOptions = cliDefinition.hasGlobalOptions();
        String commandsString = commandTreeNode.getCommandsString();
        boolean hasSpecificOptions = commandTreeNode.getCommand().getCommandTerminator().hasSpecificOptions();
        boolean hasParameters = commandTreeNode.getCommand().getCommandTerminator().hasParameters();

        String usage = executableName;

        if (hasGlobalOptions) usage += " [global options]";

        usage += " " + commandsString;

        if (hasSpecificOptions) usage += " [specific options]";

        if (hasParameters) {
            Parameters parameters = commandTreeNode.getCommand().getCommandTerminator().getParameters();
            usage += " " + parameters.getHelpUsageSubString();
        }

        return usage;
    }

    public static String indentString(String string) {
        return INDENT + string.replace("\n", "\n" + INDENT);
    }

    public static String formatStringsToCols(String col1, String col2) {

        StringBuilder stringBuilder = new StringBuilder();

        if (Strings.isSpecified(col1)) {
            stringBuilder.append(col1);
        }
        if (Strings.isSpecified(col2)) {
            Strings.fillUpAfter(stringBuilder, ' ', COL_WIDTH);
            stringBuilder.append(col2);
        }

        return stringBuilder.toString();
    }

    public static String getVersionAndDateString(CommandLineInterfaceDefinition commandLineInterfaceDefinition) {
        CommandLineInterfaceDescription commandLineInterfaceDescription
                = commandLineInterfaceDefinition.getCommandLineInterfaceDescription();

        String versionAndDate = "";

        if (commandLineInterfaceDescription.hasVersion()) {
            versionAndDate += "Version: " + commandLineInterfaceDescription.getVersion();
        }

        if (commandLineInterfaceDescription.hasDate()) {
            if (Strings.isSpecified(versionAndDate)) versionAndDate += " ";
            versionAndDate += "from: " + commandLineInterfaceDescription.getDate();
        }

        return versionAndDate;
    }

}
