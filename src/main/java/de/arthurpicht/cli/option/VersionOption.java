package de.arthurpicht.cli.option;

public class VersionOption extends Option {

    public static final String ID = "CLI_GENERIC_VERSION";

    private static final Character SHORT_NAME = 'v';
    private static final String LONG_NAME = "version";
    private static final String DESCRIPTION = "show version message and exit";

    public VersionOption() {
        super(ID, SHORT_NAME, LONG_NAME, false, null, DESCRIPTION);
    }

    @Override
    public boolean isBreaking() {
        return true;
    }

}
