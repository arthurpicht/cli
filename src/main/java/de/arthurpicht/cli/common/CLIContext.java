package de.arthurpicht.cli.common;

import java.io.PrintStream;

public class CLIContext {

//    private final PrintStream outStream;
//    private final PrintStream errorOutStream;

    public static PrintStream out;
    public static PrintStream err;

    private static CLIContext cliContext = null;

//    private CLIContext(PrintStream outStream, PrintStream errorOut) {
//        this.outStream = outStream;
//        this.errorOutStream = errorOut;
//    }

    public static void init(PrintStream outStream, PrintStream errorOutStream) {
//        cliContext = new CLIContext(outStream, errorOutStream);
        out = outStream;
        err = errorOutStream;
    }

//    public static CLIContext getInstance() {
//        if (cliContext == null) throw new IllegalStateException("CLIContext not initialized yet.");
//        return cliContext;
//    }

//    public PrintStream getOutStream() {
//        return this.outStream;
//    }
//
//    public PrintStream getErrorOutStream() {
//        return this.errorOutStream;
//    }

//    public static PrintStream out() {
//        return CLIContext.getInstance().getOutStream();
//    }
//
//    public static PrintStream err() {
//        return CLIContext.getInstance().getErrorOutStream();
//    }

}
