package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CommandLineInterfaceDefinition;
import de.arthurpicht.cli.option.Options;

public class HelpFormatter {

    public static final String INDENT = "  ";

    public static void printGlobalOptionsHelpString(CommandLineInterfaceDefinition commandLineInterfaceDefinition) {

        if (!commandLineInterfaceDefinition.hasGlobalOptions()) return;

        System.out.println("Global options:");

        Options globalOptions = commandLineInterfaceDefinition.getGlobalOptions();
        System.out.println(globalOptions.getHelpString());
    }

    public static String formatString(String string) {
        return string.replace("\n", "\n" + INDENT);
    }


}
