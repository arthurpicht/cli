package de.arthurpicht.cli.common;

import de.arthurpicht.cli.CliResult;

public class ParsingBrokenEvent extends Throwable {

    private final CliResult cliResult;

    public ParsingBrokenEvent(CliResult cliResult) {
        this.cliResult = cliResult;
    }

    public CliResult getCliResult() {
        return cliResult;
    }
}
