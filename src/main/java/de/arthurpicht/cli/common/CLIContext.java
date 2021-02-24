package de.arthurpicht.cli.common;

import java.io.PrintStream;

public class CLIContext {

    public static PrintStream out;
    public static PrintStream err;

    public static void init(PrintStream outStream, PrintStream errorOutStream) {
        out = outStream;
        err = errorOutStream;
    }

}
