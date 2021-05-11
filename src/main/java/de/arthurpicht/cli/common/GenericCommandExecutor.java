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

        // TODO clean-up if-blocks/return

        CliResult cliResult = cliCall.getCliResult();
        CliDefinition cliDefinition = cliCall.getCliDefinition();
        OptionParserResult optionParserResultGlobal = cliResult.getOptionParserResultGlobal();

        if (optionParserResultGlobal.hasOption(HelpOption.ID)) {
            //noinspection IfStatementWithIdenticalBranches
            if (isOnlyDefaultCommand(cliCall)) {
                new HelpFormatterDefaultOnly().out(cliCall);
                return;
            } else {
                new HelpFormatterGlobal().out(cliCall);
                return;
            }
        } else if (optionParserResultGlobal.hasOption(VersionOption.ID)) {
            String versionString = HelpFormatterCommons.getFullVersionText(cliDefinition);
            CLIContext.out.println(versionString);
            return;
        } else if (optionParserResultGlobal.hasOption(ManOption.ID)) {
            new HelpFormatterMan().out(cliCall);
            return;
        }

        if (!cliResult.hasSpecificOptions()) return;
        OptionParserResult optionParserResultSpecific = cliResult.getOptionParserResultSpecific();

        if (optionParserResultSpecific.hasOption(HelpOption.ID)) {
            new HelpFormatterCommand().out(cliCall);
            //noinspection UnnecessaryReturnStatement
            return;
        }
    }

    private static boolean isOnlyDefaultCommand(CliCall cliCall) {
        CliDefinition cliDefinition = cliCall.getCliDefinition();
        return (cliDefinition.hasDefaultCommand() && !cliDefinition.hasCommands());
    }

}
