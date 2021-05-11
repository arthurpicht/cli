package de.arthurpicht.cli.common;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.help.*;
import de.arthurpicht.cli.option.HelpOption;
import de.arthurpicht.cli.option.ManOption;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.option.VersionOption;

public class GenericCommandExecutor implements CommandExecutor {

    public static void apply(CliCall cliCall) throws CommandExecutorException {
        GenericCommandExecutor commandExecutor = new GenericCommandExecutor();
        commandExecutor.execute(cliCall);
    }

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {

        CliResult cliResult = cliCall.getCliResult();
        CliDefinition cliDefinition = cliCall.getCliDefinition();
        OptionParserResult optionParserResultGlobal = cliResult.getOptionParserResultGlobal();

        if (optionParserResultGlobal.hasOption(HelpOption.ID)) {

            if (isOnlyDefaultCommand(cliCall)) {
                new HelpFormatterDefaultOnly().out(cliCall);
            } else {
                new HelpFormatterGlobal().out(cliCall);
            }
        } else if (optionParserResultGlobal.hasOption(VersionOption.ID)) {

            String versionString = HelpFormatterCommons.getFullVersionText(cliDefinition);
            CLIContext.out.println(versionString);
        } else if (optionParserResultGlobal.hasOption(ManOption.ID)) {

            new HelpFormatterMan().out(cliCall);
        } else if (cliResult.hasSpecificOptions()) {

            OptionParserResult optionParserResultSpecific = cliResult.getOptionParserResultSpecific();

            if (optionParserResultSpecific.hasOption(HelpOption.ID)) {
                new HelpFormatterCommand().out(cliCall);
            }
        }
    }

    private static boolean isOnlyDefaultCommand(CliCall cliCall) {
        CliDefinition cliDefinition = cliCall.getCliDefinition();
        return (cliDefinition.hasDefaultCommand() && !cliDefinition.hasCommands());
    }

}
