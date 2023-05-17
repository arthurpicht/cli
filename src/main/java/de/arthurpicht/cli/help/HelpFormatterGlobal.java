package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CliDefinition;
import de.arthurpicht.cli.CliDescription;
import de.arthurpicht.cli.command.tree.CommandTree;
import de.arthurpicht.cli.option.Options;

public class HelpFormatterGlobal {

    public void out(CliCall cliCall) {

        CliDefinition cliDefinition = cliCall.getCliDefinition();
        CliDescription cliDescription = cliDefinition.getCliDescription();
        CommandTree commandTree = cliCall.getCliDefinition().getCommandTree();

        Printer.printHeaderString(cliDefinition);

        Printer.printExecutableDescription(cliDescription);

        Printer.printUsage(cliDefinition, commandTree);

        if (cliDefinition.hasGlobalOptions()) {
            Options globalOptions = cliDefinition.getGlobalOptions();
            Printer.printOptions(globalOptions, "Global Options:");
        }
    }

}
