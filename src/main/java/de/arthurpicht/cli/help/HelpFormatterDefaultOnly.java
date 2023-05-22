package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CliDefinition;
import de.arthurpicht.cli.CliDescription;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.console.Console;

public class HelpFormatterDefaultOnly {

    public void out(CliCall cliCall) {
        CliDefinition cliDefinition = cliCall.getCliDefinition();
        CliDescription cliDescription = cliDefinition.getCliDescription();
        DefaultCommand defaultCommand = cliDefinition.getDefaultCommand();

        Printer.printHeaderString(cliDefinition);

        Printer.printExecutableDescription(cliDescription);

        Printer.printUsageDefaultOnly(cliDefinition);

        if (defaultCommand.hasDescription()) {
            Console.println(HelpFormatterCommons.indentString(defaultCommand.getDescription()));
        }

        if (cliDefinition.hasGlobalOptions()) {
            Options globalOptions = cliDefinition.getGlobalOptions();
            Printer.printOptions(globalOptions, "Options:");
        }

        if (defaultCommand.hasParameters()) {
            Printer.printParameters(defaultCommand.getParameters());
        }
    }

}
