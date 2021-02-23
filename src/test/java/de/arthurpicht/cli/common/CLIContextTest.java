package de.arthurpicht.cli.common;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class CLIContextTest {

    @Test
    public void test() {

        ByteArrayOutputStream outBAOS = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outBAOS);

        CLIContext.init(out, new PrintStream(new ByteArrayOutputStream()));
        out.println("Test");
        out.println("Test2");

        System.out.println(outBAOS.toString());

    }


}