package de.arthurpicht.cli.option;

public class HelpOption extends Option {

    public static final String ID = "CLI_GENERIC_HELP";

    private static final Character SHORT_NAME = 'h';
    private static final String LONG_NAME = "help";
    private static final String DESCRIPTION = "Show help message and exit.";

    public HelpOption() {
        super(ID, SHORT_NAME, LONG_NAME, false, null, DESCRIPTION);
    }

    @Override
    public boolean isBreaking() {
        return true;
    }
}
