package de.arthurpicht.cli.option;

public class HelpOption extends Option {

    private static final String ID = "HELP";
    private static final Character SHORT_NAME = 'h';
    private static final String LONG_NAME = "help";
    private static final String DESCRIPTION = "Show help message and exit.";

    /**
     * Usage of Option constructor is discouraged as signature will change in further development.
     * Use OptionBuilder instead.
     *
     */
    public HelpOption() {
        super("id", SHORT_NAME, LONG_NAME, false, null, DESCRIPTION);
    }

    @Override
    public boolean isBreaking() {
        return true;
    }
}
