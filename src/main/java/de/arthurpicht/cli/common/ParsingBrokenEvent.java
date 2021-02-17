package de.arthurpicht.cli.common;

import de.arthurpicht.cli.CommandLineInterfaceResult;

public class ParsingBrokenEvent extends Throwable {

    private final CommandLineInterfaceResult commandLineInterfaceResult;

    public ParsingBrokenEvent(CommandLineInterfaceResult commandLineInterfaceResult) {
        this.commandLineInterfaceResult = commandLineInterfaceResult;
    }

    public CommandLineInterfaceResult getCommandLineInterfaceResult() {
        return commandLineInterfaceResult;
    }
}
