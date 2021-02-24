package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CliDefinition;
import de.arthurpicht.cli.CliDescription;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.common.CLIContext;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;

import static de.arthurpicht.cli.help.HelpFormatterCommons.INDENT;

public class HelpFormatterDefaultOnly {

    public void out(CliCall cliCall) {

        CliDefinition cliDefinition = cliCall.getCliDefinition();
        CliDescription cliDescription = cliDefinition.getCliDescription();
        DefaultCommand defaultCommand = cliDefinition.getDefaultCommand();

        HelpFormatterCommons.printHeaderString(cliDefinition);

        HelpFormatterCommons.printExecutableDescription(cliDescription);

        CLIContext.out.println("Usage:");
        CLIContext.out.println(INDENT + HelpFormatterCommons.getUsageOfDefaultCommand(cliDefinition, true));

        if (defaultCommand.hasDescription()) {
            CLIContext.out.println(HelpFormatterCommons.indentString(defaultCommand.getDescription()));
        }

        if (cliDefinition.hasGlobalOptions()) {
            CLIContext.out.println("Options:");
            Options globalOptions = cliDefinition.getGlobalOptions();
            CLIContext.out.println(HelpFormatterCommons.indentString(globalOptions.getHelpString()));
        }

        if (defaultCommand.hasParameters()) {
            CLIContext.out.println("Parameters:");
            Parameters parameters = defaultCommand.getParameters();
            CLIContext.out.println(HelpFormatterCommons.indentString(parameters.getHelpString()));
        }
    }

}
