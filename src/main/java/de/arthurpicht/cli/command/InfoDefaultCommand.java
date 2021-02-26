package de.arthurpicht.cli.command;

import de.arthurpicht.cli.common.InfoCommandExecutor;

public class InfoDefaultCommand extends DefaultCommand {

    public InfoDefaultCommand() {
        super(null, new InfoCommandExecutor(), "Shows basic info.");
    }

    @Override
    public boolean includeIntoHelpText() {
        return false;
    }

}
