package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CommandLineInterfaceDefinition;
import de.arthurpicht.cli.CommandLineInterfaceDescription;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.utils.core.strings.Strings;

public class HelpFormatter {

    public static final String INDENT = "  ";




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

    public static String getUsageOfDefaultCommand(CommandLineInterfaceDefinition commandLineInterfaceDefinition) {

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


    public static void printGlobalOptionsHelpString(CommandLineInterfaceDefinition commandLineInterfaceDefinition) {

        if (!commandLineInterfaceDefinition.hasGlobalOptions()) return;

        System.out.println("Global options:");

        Options globalOptions = commandLineInterfaceDefinition.getGlobalOptions();
        System.out.println(globalOptions.getHelpString());
    }

    public static String indentString(String string) {
        return INDENT + string.replace("\n", "\n" + INDENT);
    }

    public static String formatStringsToCols(String col1, String col2) {

//        boolean isSpecifiedCol1 = Strings.isSpecified(col1);
//        boolean isSpecifiedCol2 = Strings.isSpecified(col2);

        StringBuilder stringBuilder = new StringBuilder();

//        if (isSpecifiedCol1 && isSpecifiedCol2) {
//            stringBuilder.append(INDENT);
//        }
        if (Strings.isSpecified(col1)) {
            stringBuilder.append(col1);
        }
        if (Strings.isSpecified(col2)) {
            Strings.fillUpAfter(stringBuilder, ' ', HelpFormatterCommand.COL_WIDTH);
            stringBuilder.append(col2);
        }

        return stringBuilder.toString();
    }

}
