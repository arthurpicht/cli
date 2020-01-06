package de.arthurpicht.cli.common;

public class NotYetParsedException extends IllegalStateException {

    public NotYetParsedException() {
        super("Not yet parsed.");
    }

}
