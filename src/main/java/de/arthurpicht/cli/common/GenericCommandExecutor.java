package de.arthurpicht.cli.common;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.help.HelpFormatterCommand;
import de.arthurpicht.cli.help.HelpFormatterDefaultOnly;
import de.arthurpicht.cli.help.HelpFormatterGlobal;
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
            if (isOnlyDefaultCommand(commandLineInterfaceCall)) {
                new HelpFormatterDefaultOnly().out(commandLineInterfaceCall);
                return;
            } else {
                HelpFormatterGlobal.out(commandLineInterfaceCall);
                return;
            }
        } else if (optionParserResultGlobal.hasOption(VersionOption.ID)) {
            String versionString = HelpFormatterCommand.getVersionAndDateString(cliDefinition);
            System.out.println(versionString);
            return;
        }

        if (!cliResult.hasSpecificOptions()) return;
        OptionParserResult optionParserResultSpecific = cliResult.getOptionParserResultSpecific();

        if (optionParserResultSpecific.hasOption(HelpOption.ID)) {
            HelpFormatterCommand.out(commandLineInterfaceCall);
            return;
        }

    }

    private static boolean isOnlyDefaultCommand(CommandLineInterfaceCall commandLineInterfaceCall) {
        CommandLineInterfaceDefinition commandLineInterfaceDefinition = commandLineInterfaceCall.getCommandLineInterfaceDefinition();
        return (commandLineInterfaceDefinition.hasDefaultCommand() && !commandLineInterfaceDefinition.hasCommands());
    }


}
