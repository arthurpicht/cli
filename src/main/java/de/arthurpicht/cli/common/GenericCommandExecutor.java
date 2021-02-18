package de.arthurpicht.cli.common;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.cli.CommandLineInterfaceCall;
import de.arthurpicht.cli.CommandLineInterfaceResult;
import de.arthurpicht.cli.help.HelpFormatter;
import de.arthurpicht.cli.option.HelpOption;
import de.arthurpicht.cli.option.OptionParserResult;

public class GenericCommandExecutor implements CommandExecutor {

    public static void apply(CommandLineInterfaceCall commandLineInterfaceCall) throws CommandExecutorException {
        GenericCommandExecutor commandExecutor = new GenericCommandExecutor();
        commandExecutor.execute(commandLineInterfaceCall);
    }

    @Override
    public void execute(CommandLineInterfaceCall commandLineInterfaceCall) throws CommandExecutorException {

        System.out.println("Execute GenericCommandExecutor ...");

        CommandLineInterfaceResult cliResult = commandLineInterfaceCall.getCommandLineInterfaceResult();
        OptionParserResult optionParserResultGlobal = cliResult.getOptionParserResultGlobal();

        if (optionParserResultGlobal.hasOption(HelpOption.ID)) {
            //TODO
            System.out.println("Global Help ...");
            return;
        }

        if (!cliResult.hasSpecificOptions()) return;
        OptionParserResult optionParserResultSpecific = cliResult.getOptionParserResultSpecific();

        System.out.println("   processing specific options ...");

        if (optionParserResultSpecific.hasOption(HelpOption.ID)) {
            HelpFormatter.out(commandLineInterfaceCall);
            return;
        }

    }


}
