package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CommandLineInterfaceCall;
import de.arthurpicht.cli.CommandLineInterfaceDefinition;
import de.arthurpicht.cli.CommandLineInterfaceDescription;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.tree.CommandTree;
import de.arthurpicht.cli.command.tree.CommandTreeNode;
import de.arthurpicht.cli.parameter.Parameters;

import java.util.Set;

import static de.arthurpicht.cli.help.HelpFormatter.INDENT;
import static de.arthurpicht.cli.help.HelpFormatter.getUsageOfDefaultCommand;

public class HelpFormatterGlobal {

    public static void out(CommandLineInterfaceCall commandLineInterfaceCall) {

        CommandLineInterfaceDefinition commandLineInterfaceDefinition = commandLineInterfaceCall.getCommandLineInterfaceDefinition();
        CommandLineInterfaceDescription commandLineInterfaceDescription = commandLineInterfaceDefinition.getCommandLineInterfaceDescription();
        CommandTree commandTree = commandLineInterfaceCall.getCommandLineInterfaceDefinition().getCommandTree();
        Set<CommandTreeNode> terminatedNodes = commandTree.getTerminatedNodes();

        if (commandLineInterfaceDefinition.hasDefaultCommand() && !commandLineInterfaceCall.getCommandLineInterfaceDefinition().hasCommands()) {
            HelpFormatterCommand.out(commandLineInterfaceCall);
            return;
        }

        System.out.println(HelpFormatter.getHeaderString(commandLineInterfaceDefinition));

        if (commandLineInterfaceDescription.hasDescription()) {
            String description = commandLineInterfaceDescription.getDescription();
            System.out.println(HelpFormatter.indentString(description));
        }

        System.out.println("Usage:");

        if (commandLineInterfaceDefinition.hasDefaultCommand()) {
            System.out.println(INDENT + getUsageOfDefaultCommand(commandLineInterfaceDefinition));
        }

        if (commandTree.hasCommands()) {
            for (CommandTreeNode commandTreeNode : terminatedNodes) {
                String usageString = getSpecificUsage(commandTreeNode, commandLineInterfaceDefinition);
                System.out.println(INDENT + usageString);
            }
        }

        HelpFormatter.printGlobalOptionsHelpString(commandLineInterfaceDefinition);

    }


    private static String getHeaderString(CommandLineInterfaceDefinition cliDefinition) {

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

    private static String getDefaultUsage(CommandLineInterfaceDefinition commandLineInterfaceDefinition) {

        String executableName = commandLineInterfaceDefinition.getCommandLineInterfaceDescription().getExecutableName();
        boolean hasGlobalOptions = commandLineInterfaceDefinition.hasGlobalOptions();
        DefaultCommand defaultCommand = commandLineInterfaceDefinition.getDefaultCommand();

        String usage = executableName;

        if (hasGlobalOptions) usage += " [global options]";

        if (defaultCommand.hasParameters()) {
            Parameters parameters = defaultCommand.getParameters();
            usage += " " + parameters.getHelpUsageSubString();
        }

        return usage;
    }

    private static String getGlobalUsage(CommandLineInterfaceDefinition cliDefinition) {

        String executableName = cliDefinition.getCommandLineInterfaceDescription().getExecutableName();
        boolean hasGlobalOptions = cliDefinition.hasGlobalOptions();
        boolean hasCommands = cliDefinition.hasCommands();

        String globalUsage = executableName;
        if (hasGlobalOptions) {
            globalUsage += " [globalOptions]";
        }
        if (hasCommands) {
            globalUsage += " {COMMANDS}";
        }
        return globalUsage;
    }

    private static void printDescription(CommandLineInterfaceDefinition commandLineInterfaceDefinition) {

        if (commandLineInterfaceDefinition.getCommandLineInterfaceDescription().hasDescription()) {

        }
        System.out.println(commandLineInterfaceDefinition.getCommandLineInterfaceDescription().getDescription());
    }


    private static String getSpecificUsage(CommandTreeNode commandTreeNode, CommandLineInterfaceDefinition cliDefinition) {

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

}
