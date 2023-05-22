package de.arthurpicht.cli.command;

import de.arthurpicht.cli.common.InfoCommandExecutor;

public class InfoDefaultCommand extends DefaultCommand {

    public InfoDefaultCommand() {
        super(null, new InfoCommandExecutor(), "shows basic info");
    }

    @Override
    public boolean includeIntoHelpText() {
        return false;
    }

}
