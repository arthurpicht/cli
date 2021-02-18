package de.arthurpicht.cli.common;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.help.HelpFormatter;
import de.arthurpicht.cli.option.HelpOption;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.option.VersionOption;

public class GenericCommandExecutor implements CommandExecutor {

    public static void apply(CommandLineInterfaceCall commandLineInterfaceCall) throws CommandExecutorException {
        GenericCommandExecutor commandExecutor = new GenericCommandExecutor();
        commandExecutor.execute(commandLineInterfaceCall);
    }

    @Override
    public void execute(CommandLineInterfaceCall commandLineInterfaceCall) throws CommandExecutorException {

        CommandLineInterfaceResult cliResult = commandLineInterfaceCall.getCommandLineInterfaceResult();
        CommandLineInterfaceDefinition cliDefinition = commandLineInterfaceCall.getCommandLineInterfaceDefinition();
        OptionParserResult optionParserResultGlobal = cliResult.getOptionParserResultGlobal();

        if (optionParserResultGlobal.hasOption(HelpOption.ID)) {
            //TODO
            System.out.println("Global Help ...");
            return;
        } else if (optionParserResultGlobal.hasOption(VersionOption.ID)) {
            String versionString = HelpFormatter.getVersionAndDateString(cliDefinition);
            System.out.println(versionString);
            return;
        }

        if (!cliResult.hasSpecificOptions()) return;
        OptionParserResult optionParserResultSpecific = cliResult.getOptionParserResultSpecific();

        if (optionParserResultSpecific.hasOption(HelpOption.ID)) {
            HelpFormatter.out(commandLineInterfaceCall);
            return;
        }

    }

}
