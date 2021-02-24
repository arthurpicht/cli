package de.arthurpicht.cli.option;

public class ManOption extends Option {

    public static final String ID = "CLI_GENERIC_MAN";

    private static final Character SHORT_NAME = 'm';
    private static final String LONG_NAME = "man";
    private static final String DESCRIPTION = "Show manual and exit.";

    public ManOption() {
        super(ID, SHORT_NAME, LONG_NAME, false, null, DESCRIPTION);
    }

    @Override
    public boolean isBreaking() {
        return true;
    }
}
