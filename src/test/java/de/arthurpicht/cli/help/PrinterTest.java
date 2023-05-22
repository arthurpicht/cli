package de.arthurpicht.cli.help;

import de.arthurpicht.cli.PrintTestContext;
import de.arthurpicht.cli.option.Option;
import de.arthurpicht.cli.option.Options;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrinterTest {

    @Test
    public void printOptions() {

        Options options = new Options()
                .add(new Option("A", 'A', "almost-all", false, "", "do not list implied . and .."))
                .add(new Option("a", 'a', "all", false, "", "do not hide entries starting with ."))
                .add(new Option("B", 'B', "ignore-backups", false, "", "do not hide entries starting with ."))
                .add(new Option("b", 'b', "escape", false, "", "print octal escapes for nongraphic characters"))
                .add(new Option("bs", null, "block-size", true, "SIZE", "use SIZE-byte blocks"))
                .add(new Option("c", 'c', null, false, "", "with -lt: sort by, and show ctime"))
                .add(new Option("C", 'C', null, true, "DUMMY", "list entries by columns"));

        PrintTestContext printTestContext = new PrintTestContext();
        printTestContext.configureConsole();

        Printer.printOptions(options, "Global Options:");

        String output = printTestContext.getOutput();
        System.out.println(output);

        assertEquals("Global Options:\n" +
                "  -a, --all                     do not hide entries starting with .\n" +
                "  -A, --almost-all              do not list implied . and ..\n" +
                "      --block-size <SIZE>       use SIZE-byte blocks\n" +
                "  -b, --escape                  print octal escapes for nongraphic characters\n" +
                "  -B, --ignore-backups          do not hide entries starting with .\n" +
                "  -c                            with -lt: sort by, and show ctime\n" +
                "  -C <DUMMY>                    list entries by columns\n", output);
    }

}