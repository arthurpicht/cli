package de.arthurpicht.cli.option;

import de.arthurpicht.cli.TestOut;
import org.junit.jupiter.api.Test;

import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.*;

class OptionsHelpStringTest {

    @Test
    void helpString() {

        Options options = new Options()
                .add(new Option("A", 'A', "almost-all", false, "", "do not list implied . and .."))
                .add(new Option("a", 'a', "all", false, "", "do not hide entries starting with ."))
                .add(new Option("B", 'B', "ignore-backups", false, "", "do not hide entries starting with ."))
                .add(new Option("b", 'b', "escape", false, "", "print octal escapes for nongraphic characters"))
                .add(new Option("bs", null, "block-size", true, "SIZE", "use SIZE-byte blocks"))
                .add(new Option("c", 'c', null, false, "", "with -lt: sort by, and show ctime"))
                .add(new Option("C", 'C', null, false, "", "list entries by columns"));

        String helpString = options.getHelpString();

        TestOut.println(helpString);

        assertEquals("-a, --all                     do not hide entries starting with .\n" +
                "-A, --almost-all              do not list implied . and ..\n" +
                "    --block-size <SIZE>       use SIZE-byte blocks\n" +
                "-b, --escape                  print octal escapes for nongraphic characters\n" +
                "-B, --ignore-backups          do not hide entries starting with .\n" +
                "-c                            with -lt: sort by, and show ctime\n" +
                "-C                            list entries by columns", helpString);
    }

}