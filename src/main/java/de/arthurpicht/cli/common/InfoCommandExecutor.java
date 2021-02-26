package de.arthurpicht.cli.common;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CliDescription;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.cli.option.HelpOption;
import de.arthurpicht.cli.option.ManOption;

public class InfoCommandExecutor implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {

        CliDescription cliDescription = cliCall.getCliDefinition().getCliDescription();
        String executableName = cliDescription.getExecutableName();
        boolean hasGlobalHelp = cliCall.getCliDefinition().getGlobalOptions().hasOptionWithId(HelpOption.ID);
        String helpOptionLongName = new HelpOption().getLongName();
        boolean hasMan = cliCall.getCliDefinition().getGlobalOptions().hasOptionWithId(ManOption.ID);
        String manOptionLongName = new ManOption().getLongName();

        String info = executableName;
        boolean secondLine = false;

        if (cliDescription.hasDescription()) {
            info += " - " + cliDescription.getDescriptionFirstLine();
            secondLine = true;
        } else {
            info += ".";
        }

        if (hasGlobalHelp || hasMan) {
            if (secondLine) {
                info += "\n";
            } else {
                info += " ";
            }
            info += "Call ";
        }

        if (hasGlobalHelp && hasMan) {
            info += "\"" + executableName + " --" + helpOptionLongName + "\" or \"" + executableName + " --" + manOptionLongName + "\" for more info.";
            CLIContext.out.println(info);
            return;
        }

        if (hasGlobalHelp) {
            info += "\"" + executableName + " --" + helpOptionLongName + "\" for more info.";
            CLIContext.out.println(info);
        }

        if (hasMan) {
            info += "\"" + executableName + " --" + manOptionLongName + "\" for more info.";
            CLIContext.out.println(info);
        }
    }

}
