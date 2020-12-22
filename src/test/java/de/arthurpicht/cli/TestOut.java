package de.arthurpicht.cli;

import static de.arthurpicht.cli.TestConfig.OUT;

public class TestOut {

    public static void println(String string) {
        if (OUT) System.out.println(string);
    }

    public static void println(int i) {
        if (OUT) System.out.println(i);
    }

    public static void printStacktrace(Exception e) {
        if (OUT) e.printStackTrace();
    }

}
